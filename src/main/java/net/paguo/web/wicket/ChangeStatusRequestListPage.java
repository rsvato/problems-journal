package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.controller.ChangeStatusRequestController;
import static net.paguo.controller.ChangeStatusRequestController.SortProperties.*;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.RequestInformation;
import net.paguo.domain.requests.RequestNextStage;
import static net.paguo.domain.users.ApplicationRole.Action.*;
import net.paguo.web.wicket.hardcopy.RequestsReportPanel;
import net.paguo.web.wicket.components.ConfirmationLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.table.*;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;

/**
 * User: sreentenko Date: 02.02.2008 Time: 1:30:54
 */
public class ChangeStatusRequestListPage extends SettingsAwarePage {

    @SpringBean
    private ChangeStatusRequestController controller;
    private static final long serialVersionUID = -7948688911485787483L;
    private static final Log log = LogFactory.getLog(ChangeStatusRequestListPage.class);
    private ChangeStatusRequestProvider provider;

    public ChangeStatusRequestController getController() {
        return controller;
    }

    public void setController(ChangeStatusRequestController controller) {
        this.controller = controller;
    }

    public void setSettingsController(ApplicationSettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public ChangeStatusRequestProvider getProvider() {
        if (provider == null) {
            provider = new ChangeStatusRequestProvider();
        }
        return provider;
    }

    public void setProvider(ChangeStatusRequestProvider provider) {
        this.provider = provider;
    }

    public ChangeStatusRequestListPage() {
        DataTable table = new DataTable("table", getColumns(),
                getProvider(), getPerPageSettings()) {
            private static final long serialVersionUID = -7374521421166015094L;

            protected Item newRowItem(String id, int index, IModel model) {
                return new RequestItem(id, index, model);
            }
        };
        table.addTopToolbar(new NavigationToolbar(table));
        table.addTopToolbar(new HeadersToolbar(table, getProvider()));
        secureElement(table, ChangeStatusRequest.class, VIEW);
        add(table);
        add(HeaderContributor.forCss(ChangeStatusRequestListPage.class, "wstyles.css"));
        final BookmarkablePageLink child = new BookmarkablePageLink("createNew", ChangeStatusRequestCreatePage.class);
        secureElement(child, ChangeStatusRequest.class, CREATE);
        add(new RequestsReportPanel("download"));
        add(child);
    }

    private IColumn[] getColumns() {
        return new IColumn[]{
                new TextFilteredPropertyColumn(new ResourceModel("client"), CLIENT.getLabel(), "showClient"),
                new PropertyColumn(new ResourceModel("address"), "discAddress"),
                new DatetimePropertyColumn(new ResourceModel("request.cancel.date"), DISC_REQ_DATE.getLabel(),
                        "cancelRequest.dateEntered"),
                new PropertyColumn(new ResourceModel("request.creator"), "cancelRequest.author"),
                new DatetimePropertyColumn(new ResourceModel("exec.cancel.date"), DISC_EXEC_DATE.getLabel(),
                        "cancelExec.dateEntered"),
                new PropertyColumn(new ResourceModel("endpoint"), "endpoint"),
                new PropertyColumn(new ResourceModel("request.executor"), "cancelExec.author"),
                new DatetimePropertyColumn(new ResourceModel("request.restore.date"), CON_REQ_DATE.getLabel(),
                        "restoreRequest.dateEntered"),
                new PropertyColumn(new ResourceModel("request.creator"), "restoreRequest.author"),
                new DatetimePropertyColumn(new ResourceModel("exec.restore.date"), CON_EXEC_DATE.getLabel(),
                        "restoreExec.dateEntered"),
                new PropertyColumn(new ResourceModel("request.executor"), "restoreExec.author"),
                new AbstractColumn(new Model("")) {
                    private static final long serialVersionUID = 5072325276734644024L;

                    public void populateItem(Item item, String s, IModel iModel) {
                        final RequestActionPanel panel = new RequestActionPanel(s, iModel);
                        item.add(panel);
                    }
                }
        };
    }

    private static class DatetimePropertyColumn extends PropertyColumn {
        private static final long serialVersionUID = 8271745206935928955L;

        public DatetimePropertyColumn(IModel displayModel, String sortProperty, String propertyExpression) {
            super(displayModel, sortProperty, propertyExpression);
        }

        @Override
        public void populateItem(Item item, String componentId, IModel model) {
            item.add(DateLabel.forDatePattern(componentId, new PropertyModel(model, getPropertyExpression()),
                    "dd-MM-yy HH:mm"));
        }
    }

    private static class ChangeStatusRequestProvider extends SortableDataProvider {
        private static final long serialVersionUID = 1059602856583191508L;

        public ChangeStatusRequestProvider() {
            InjectorHolder.getInjector().inject(this);
            setSort(DISC_REQ_DATE.getLabel(), false);
        }

        @SpringBean(name = "changeStatusRequestController")
        private ChangeStatusRequestController controller;

        public ChangeStatusRequestController getController() {
            return controller;
        }

        public void setController(ChangeStatusRequestController controller) {
            this.controller = controller;
        }

        public Iterator iterator(int first, int count) {
            return getController().getSortableIterator(first, count, getSort().getProperty(), getSort().isAscending());
        }

        public int size() {
            return getController().getTotalCount();
        }

        public IModel model(Object object) {
            return new Model((ChangeStatusRequest) object);
        }
    }

    private class RequestActionPanel extends Panel {
        private static final long serialVersionUID = 79647500481887377L;
        private ChangeStatusRequest changeRequest;
        @SpringBean(name = "changeStatusRequestController")
        private ChangeStatusRequestController controller;

        public ChangeStatusRequest getChangeRequest() {
            return changeRequest;
        }

        public void setChangeRequest(ChangeStatusRequest request) {
            this.changeRequest = request;
        }

        public ChangeStatusRequestController getController() {
            return controller;
        }

        public void setController(ChangeStatusRequestController controller) {
            this.controller = controller;
        }

        public RequestActionPanel(String s, final IModel model) {
            super(s, model);
            this.changeRequest = (ChangeStatusRequest) model.getObject();
            final Link confirmCancelLink = new Link("confirmCancel") {
                public void onClick() {
                    setResponsePage(new EnterEndpointPage(model));
                }
            };
            confirmCancelLink.setVisible(EnumSet.of(RequestNextStage.CANCEL_CONFIRM).contains(changeRequest.getNextStage()));
            secureElement(confirmCancelLink, ChangeStatusRequest.class, CHANGE);
            add(confirmCancelLink);

            final RequestProgressLink restoreRequestLink = new RequestProgressLink("requestRestore", changeRequest, RequestNextStage.RESTORE_REQUEST);
            secureElement(restoreRequestLink, RequestInformation.class, CREATE);
            add(restoreRequestLink);

            final RequestProgressLink restoreExecuteLink = new RequestProgressLink("confirmRestore", changeRequest, RequestNextStage.RESTORE_CONFIRM);
            add(restoreExecuteLink);
            secureElement(restoreExecuteLink, RequestInformation.class, CHANGE);

            final RequestRewindLink undoStateLink = new RequestRewindLink("rewindRequest", changeRequest);
            secureElement(undoStateLink, ChangeStatusRequest.class, OVERRIDE);
            add(undoStateLink);

            PageParameters params = new PageParameters();
            params.put("requestId", changeRequest.getId());
            final BookmarkablePageLink changeLink = new BookmarkablePageLink("edit", ChangeStatusRequestCreatePage.class, params);
            secureElement(changeLink, ChangeStatusRequest.class, OVERRIDE);
            add(changeLink);

            final ConfirmationLink confirmationLink = new ConfirmationLink("delete") {
                public void onClick() {
                    try {
                        getController().deleteRequest(changeRequest);
                        Session.get().info(getLocalizer().getString("request.deleted", RequestActionPanel.this));
                    } catch (ControllerException e) {
                        Session.get().error(getLocalizer().getString("operation.error", RequestActionPanel.this));
                    }
                }
            };
            secureElement(confirmationLink, ChangeStatusRequest.class, DELETE);
            add(confirmationLink);
        }

        private RequestInformation createRequestInformation() {
            RequestInformation confirmCancel = new RequestInformation();
            confirmCancel.setAuthor(findSessionUser());
            confirmCancel.setDateEntered(new Date());
            return confirmCancel;
        }

        private class RequestProgressLink extends Link {
            private final ChangeStatusRequest request;
            private final RequestNextStage visibleStage;
            private static final long serialVersionUID = -2153982979330208120L;

            public RequestProgressLink(String id, ChangeStatusRequest request, RequestNextStage stageToBeVisible) {
                super(id);
                this.request = request;
                this.visibleStage = stageToBeVisible;
            }

            @Override
            public void onClick() {
                final RequestInformation information = createRequestInformation();
                try {
                    getController().saveAndProgress(request, information);
                } catch (ControllerException e) {
                    log.error("cannot confirm cancel of service");
                }
                setResponsePage(ChangeStatusRequestListPage.class);
            }

            public boolean isVisible() {
                return visibleStage == request.getNextStage() && getPermModidier();
            }

            protected boolean getPermModidier() {
                EnumSet<RequestNextStage> allowed = EnumSet.of(RequestNextStage.CANCEL_CONFIRM);
                if (!request.isPermanent()) {
                    allowed.addAll(EnumSet.of(RequestNextStage.RESTORE_REQUEST, RequestNextStage.RESTORE_CONFIRM));
                }
                return allowed.contains(visibleStage);
            }
        }

        private class RequestRewindLink extends Link {
            private ChangeStatusRequest request;

            public RequestRewindLink(String id, ChangeStatusRequest request) {
                super(id);
                this.request = request;
            }

            @Override
            public void onClick() {
                try {
                    getController().saveAndRewind(request);
                } catch (ControllerException e) {
                    log.error("Cannot rewind request due to controller error");
                }
                setResponsePage(ChangeStatusRequestListPage.class);
            }

            @Override
            public boolean isVisible() {
                return RequestNextStage.getPreviousStage(request.getNextStage(), request.isPermanent()) != null;
            }
        }
    }

    private class RequestItem extends Item {

        /**
         * @param id    component id
         * @param index relative index of this item in the pageable view
         * @param model model for this item
         */
        public RequestItem(String id, int index, IModel model) {
            super(id, index, model);
        }

        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            final Object o = getModelObject();
            if (o != null) {
                ChangeStatusRequest request = (ChangeStatusRequest) o;
                if (request.isPermanent()) {
                    tag.put("class", "marked");
                }
            }
        }
    }
}
