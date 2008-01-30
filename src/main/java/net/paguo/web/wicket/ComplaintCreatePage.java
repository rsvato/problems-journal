package net.paguo.web.wicket;

import net.paguo.controller.ClientItemController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.problems.ClientComplaint;
import static net.paguo.domain.users.ApplicationRole.Action.CREATE;
import static net.paguo.domain.users.ApplicationRole.Action.CHANGE;
import static net.paguo.domain.users.ApplicationRole.Action.OVERRIDE;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.Component;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

/**
 * User: sreentenko
 * Date: 21.11.2007
 * Time: 0:55:33
 */
public final class ComplaintCreatePage extends SecuredWebPage {
    private static final Log log = LogFactory.getLog(ComplaintCreatePage.class);

    @SpringBean
    private NetworkFailureController controller;

    @SpringBean
    private ClientItemController clientItemController;

    public ClientItemController getClientItemController() {
        return clientItemController;
    }

    public void setClientItemController(ClientItemController clientItemController) {
        this.clientItemController = clientItemController;
    }

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }



    public ComplaintCreatePage(PageParameters parameters){
        ClientComplaint complaint = new ClientComplaint();
        try {
            int problemId = parameters.getInt("problemId");
            complaint = getController().getClientComplaint(problemId);
            if (complaint == null){
                info("Non-existent complaint");
                complaint = new ClientComplaint();
            }
        } catch (StringValueConversionException e) {
            log.debug("Bad parameters or no parameters");
        }
        add(new FeedbackPanel("feedback"));
        final ComplaintCreateForm form = new ComplaintCreateForm("form", complaint);
        secureElement(form, ClientComplaint.class, Arrays.asList(CREATE, CHANGE, OVERRIDE));
        add(form);
    }

    void saveComplaint(ClientComplaint problem){
        final NetworkFailureController failureController = getController();
        if (failureController == null){
            error("General error: cannot establish DB connection");
        } else {
            try {
                if (problem.getId() == null){
                    //fill user field only for new problems
                    problem.setUserCreated(findSessionUser());
                }

                String clientName = problem.getEnteredClient();
                final ClientItem item = getClientItemController().findByName(clientName);
                if (item != null){
                    problem.setEnteredClient(null);
                    problem.setClient(item);
                }else{
                    final String msg = getLocalizer().getString("warning.client.not.found",
                            this, new Model(problem));
                    Session.get().warn(msg);
                }

                failureController.saveComplaint(problem);
                final String message = getLocalizer().getString("operation.successful", this);
                Session.get().info(message);
                setResponsePage(ComplaintsPage.class);
            } catch (ControllerException e) {
                log.error(e);
                //TODO: exctract message
                error("Unexpected error communicated with DB.");
            }
        }
    }

    public final class ComplaintCreateForm extends Form {

        private Collection<ClientItem> clientItems;

        public ComplaintCreateForm(String id) {
            super(id, new CompoundPropertyModel(new ClientComplaint()));
            getComplaint().setFailureTime(new java.util.Date());
            initForm();
        }

        private void initForm() {
            WebMarkupContainer main = new WebMarkupContainer("main");
            clientItems = getClientItemController().getAllClients();
            main.add(new TextArea("failureDescription").setRequired(true));
            main.add(new DateTimeField("failureTime")
                    .setRequired(true));

            main.add(new AutoCompleteTextField("enteredClient") {
                protected Iterator getChoices(String input) {
                    if (StringUtils.isEmpty(input)){
                        return Collections.EMPTY_LIST.iterator();
                    }

                    input = input.toLowerCase();

                    List<String> result = new ArrayList<String>();
                    for (ClientItem clientItem : clientItems) {
                        final String clientName = clientItem.getClientName();
                        if (!StringUtils.isEmpty(clientName)
                                && clientName.toLowerCase().startsWith(input.trim().toLowerCase())){
                            result.add(clientName);
                        }
                    }
                    return result.iterator();
                }
            });

            secureElement(main, ClientComplaint.class, CREATE, CHANGE, OVERRIDE);
            add(main);

            WebMarkupContainer p = new WebMarkupContainer("additional");

            final TextArea area = new TextArea("restoreAction.failureCause");
            area.setRequired(existingComplaint());
            p.add(area.
                    setEnabled(existingComplaint()));
            p.add(new DateTimeField("restoreAction.restoreTime").
                    setEnabled(existingComplaint()));
            p.add(new TextArea("restoreAction.restoreAction").
                    setEnabled(existingComplaint()));
            final Component box = new CheckBox("restoreAction.completed")
                    .setEnabled(existingComplaint());
            p.add(box);
            p.setVisible(existingComplaint());
            if (existingComplaint() && getComplaint().getRestoreAction() != null &&
                    getComplaint().getRestoreAction().getCompleted()){
                secureElement(p, ClientComplaint.class, OVERRIDE);
            }else{
                secureElement(p, ClientComplaint.class, CHANGE, OVERRIDE);
            }
            add(p);
        }

        private boolean existingComplaint() {
            return getComplaint().getId() != null;
        }

        public ComplaintCreateForm(String id, ClientComplaint complaint){
            super(id, new CompoundPropertyModel(complaint));
            if (complaint.getClient() != null){
                complaint.setEnteredClient(complaint.getClient().getClientName());
            }
            initForm();
        }

        @Override
        protected void onSubmit() {
            ClientComplaint enteredProblem = getComplaint();
            saveComplaint(enteredProblem);
            enteredProblem.setFailureDescription("");
            enteredProblem.setFailureTime(new java.util.Date());
        }

        private ClientComplaint getComplaint() {
            return (ClientComplaint) getModelObject();
        }
    }
}