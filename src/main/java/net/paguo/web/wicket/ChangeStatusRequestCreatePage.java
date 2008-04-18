package net.paguo.web.wicket;

import net.paguo.controller.ChangeStatusRequestController;
import net.paguo.controller.ClientItemController;
import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.clients.PostalAddress;
import net.paguo.domain.requests.*;
import static net.paguo.domain.users.ApplicationRole.Action.*;
import net.paguo.domain.users.LocalUser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.*;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: sreentenko
 * Date: 01.02.2008
 * Time: 2:05:54
 */
public class ChangeStatusRequestCreatePage extends SecuredWebPage {
    @SpringBean
    private ChangeStatusRequestController controller;

    @SpringBean
    private ClientItemController clientItemController;

    @SpringBean
    private UsersController usersController;

    private static final Log log = LogFactory.getLog(ChangeStatusRequestCreatePage.class);


    public ChangeStatusRequestCreatePage(PageParameters parameters) {
        ChangeStatusRequest request = new ChangeStatusRequest();
        try {
            final int requestId = parameters.getInt("requestId");
            request = getController().get(requestId);
        } catch (StringValueConversionException e) {
            log.debug("Bad parameter or no parameters");
        }
        final RequestForm requestForm = new RequestForm("form", request);
        secureElement(requestForm, ChangeStatusRequest.class, CREATE, CHANGE, OVERRIDE);
        add(requestForm);
        add(new FeedbackPanel("feedback"));
    }

    public ClientItemController getClientItemController() {
        return clientItemController;
    }

    public void setClientItemController(ClientItemController clientItemController) {
        this.clientItemController = clientItemController;
    }

    public ChangeStatusRequestController getController() {
        return controller;
    }

    public void setController(ChangeStatusRequestController controller) {
        this.controller = controller;
    }


    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }

    public void saveRequest(ChangeStatusRequest request) {
        if (request.getId() == null) {
            log.debug("Request is new. Adding request information");
            RequestInformation info = new RequestInformation();
            info.setAuthor(super.findSessionUser());
            info.setDateEntered(new Date());
            request.setCancelRequest(info);
        }

        /*String clientName = request.getEnteredClient();
        if (clientName != null) {
            final ClientItem item = findClient(clientName);
            if (item != null) {
                //don't drop entered client, it's good for ordering
                request.setClient(item);
            } //no warning here, as entered client is not null...
        }*/

        request.setEnteredClient(request.getClient().getClientName());

        try {
            getController().save(request);
            setResponsePage(ChangeStatusRequestListPage.class);
        } catch (ControllerException e) {
            log.error(e);
            Session.get().error("Error saving request");
        }
    }

    private ClientItem findClient(String clientName) {
        return getClientItemController().findByName(clientName.trim());
    }

    private class RequestForm extends Form {

        public RequestForm(String id, ChangeStatusRequest request) {
            super(id, new CompoundPropertyModel(request));
            Collection<ClientItem> clientItems = getClientItemController().getAllClients();
            final DropDownChoice child = new DropDownChoice("discAddress");
            child.setRequired(true);
            child.setOutputMarkupId(true);
            child.setChoiceRenderer(new IChoiceRenderer() {
                public Object getDisplayValue(Object object) {
                    return object.toString();
                }

                public String getIdValue(Object object, int index) {
                    return ((PostalAddress) object).getId().toString();
                }
            });
            if (request.getClient() != null) {
                List choices = createAddressList(request.getClient());
                child.setChoices(choices);
            }
            add(child);
            final FeedbackPanel feedbackPanel = new FeedbackPanel("formFeedback");
            feedbackPanel.setOutputMarkupId(true);
            add(new DropDownChoice("client", (List) clientItems, new IChoiceRenderer() {
                public Object getDisplayValue(Object object) {
                    return ((ClientItem) object).getClientName();
                }

                public String getIdValue(Object object, int index) {
                    return ((ClientItem) object).getId().toString();
                }
            }).setRequired(true)
                    .setEnabled(request.getId() == null)
                    .add(new ClientChangeEventBehavior(child, feedbackPanel)));
            //add(new TextArea("description"));
            add(new CheckBox("permanent"));

            final Set<Notice> notices = request.getNotices();
            if (notices == null){
                request.getNotices().add(new EmailNotice());
                request.getNotices().add(new PhoneNotice());
            }

            final RepeatingView noticesView = new RepeatingView("notices");
            for (Notice notice : notices) {
                noticesView.add(new NoticePanel(noticesView.newChildId(), notice));
            }
            add(noticesView);

            add(feedbackPanel);
            final StagesPanel panel = new StagesPanel("panel", (CompoundPropertyModel) getModel());

            secureElement(panel, ChangeStatusRequest.class, OVERRIDE);
            add(panel);

        }

        private List createAddressList(ClientItem clientItem) {
            final Set<PostalAddress> addressSet = clientItem.getAddresses();// init collection
            List choices = new ArrayList<PostalAddress>();
            choices.addAll(addressSet);
            return choices;
        }

        @Override
        protected void onSubmit() {
            saveRequest((ChangeStatusRequest) getModelObject());
        }


        private class ClientRequiredValidator extends AbstractValidator {
            private static final long serialVersionUID = -1124154415951075627L;

            @Override
            protected void onValidate(IValidatable validatable) {
                String input = (String) validatable.getValue();
                if (findClient(input) == null) {
                    error(validatable, "client.not.found");
                }

            }

            @Override
            protected String resourceKey() {
                return "ClientRequiredValidator";
            }
        }

        private class ClientChangeEventBehavior extends OnChangeAjaxBehavior {
            private DropDownChoice choiceList;
            private FeedbackPanel panel;

            public ClientChangeEventBehavior(DropDownChoice dropDownChoice, FeedbackPanel panel) {
                super();
                this.choiceList = dropDownChoice;
                this.panel = panel;
            }

            protected void onUpdate(AjaxRequestTarget target) {
                final ClientItem o = (ClientItem) getComponent().getModelObject();
                final ClientItem item = getClientItemController().findByName(o.getClientName());
                choiceList.setChoices(createAddressList(item));
                final boolean addressCount = item.getAddresses().size() > 0;
                if (!addressCount) {
                    Session.get().warn(getLocalizer()
                            .getString("addresses.not.found", ChangeStatusRequestCreatePage.this));
                }
                target.addComponent(panel);
                target.addComponent(choiceList);
            }

            @Override
            protected IAjaxCallDecorator getAjaxCallDecorator() {
                return new ProgressCallDecorator("progress");
            }
        }
    }

    private class RequestInfoFragment extends Fragment {

        public RequestInfoFragment(String id,
                                   CompoundPropertyModel reqInfo,
                                   String infoLabel, IModel labelModel, MarkupContainer markupProvider) {
            super(id, "riedit", markupProvider, reqInfo);
            add(new Label("legend", labelModel));
            add(new DateTimeField("dateEntered", new PropertyModel(reqInfo, infoLabel + ".dateEntered"))
                    .setRequired(true));
            add(new DropDownChoice("author", new PropertyModel(reqInfo, infoLabel + ".author"), (List<LocalUser>) getUsersController().getAll(), new IChoiceRenderer() {
                public Object getDisplayValue(Object object) {
                    return String.valueOf(object);
                }

                public String getIdValue(Object object, int index) {
                    return ((LocalUser) object).getId().toString();
                }
            }));
        }
    }

    private class StagesPanel extends Panel {

        public StagesPanel(String id, CompoundPropertyModel requestModel) {
            super(id, requestModel);

            final RepeatingView child = new RepeatingView("stages");
            add(child);
            String[] labels = new String[]{"cancelRequest", "cancelExec", "restoreRequest", "restoreExec"};
            int i = 0;
            ChangeStatusRequest req = (ChangeStatusRequest) getModelObject();
            for (String infoLabel : labels) {
                try {
                    String getterField = "get" + StringUtils.capitalize(infoLabel);
                    final Method method = ChangeStatusRequest.class.getMethod(getterField, null);
                    RequestInformation request = (RequestInformation) method.invoke(req);
                    if (request != null) {
                        WebMarkupContainer row = new WebMarkupContainer(child.newChildId());
                        child.add(row);
                        Component editor = new RequestInfoFragment("info", requestModel, infoLabel, new ResourceModel(labels[i]), StagesPanel.this);
                        row.add(editor);
                    }
                } catch (NoSuchMethodException e) {
                    log.error(e);
                } catch (IllegalAccessException e) {
                    log.error(e);
                } catch (InvocationTargetException e) {
                    log.error(e);
                }
                i++;
            }

        }
    }

}


