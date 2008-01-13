package net.paguo.web.wicket;

import net.paguo.controller.ClientItemController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.FailureRestore;
import net.paguo.web.wicket.auth.JournalRoles;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
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
@AuthorizeInstantiation({JournalRoles.ROLE_CHANGE_COMPLAINT.name(),
        JournalRoles.ROLE_OVERRIDE_COMPLAINT.name(),
        JournalRoles.ROLE_CREATE_COMPLAINT.name()})
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

    public ComplaintCreatePage() {
        add(new ComplaintCreateForm("form"));
        add(new FeedbackPanel("feedback"));
    }

    public ComplaintCreatePage(PageParameters parameters){
        int problemId = parameters.getInt("problemId");
        ClientComplaint problem = getController().getClientComplaint(problemId);
        if (problem == null){
            info("Non-existent problem");
            problem = new ClientComplaint();
        }
        add(new FeedbackPanel("feedback"));
        add(new ComplaintCreateForm("form", problem));
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
            getNetworkProblem().setFailureTime(new java.util.Date());
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

            add(main);

            WebMarkupContainer p = new WebMarkupContainer("additional");

            final TextArea area = new TextArea("restoreAction.failureCause");
            area.setRequired(existingProblem());
            p.add(area.
                    setEnabled(existingProblem()));
            p.add(new DateTimeField("restoreAction.restoreTime").
                    setEnabled(existingProblem()));
            p.add(new TextArea("restoreAction.restoreAction").
                    setEnabled(existingProblem()));
            p.add(new CheckBox("restoreAction.completed")
                    .setEnabled(existingProblem()));

            add(p);
            p.setVisible(existingProblem());
            
            StringBuilder roles = new StringBuilder(JournalRoles.ROLE_CHANGE_COMPLAINT.name());
            final FailureRestore restore = getNetworkProblem().getRestoreAction();
            if (restore != null && restore.getCompleted()){
                roles.append(",").append(JournalRoles.ROLE_OVERRIDE_COMPLAINT.name());
            }
            MetaDataRoleAuthorizationStrategy.authorize(p, RENDER, roles.toString());
            MetaDataRoleAuthorizationStrategy.authorize(main, RENDER,
                    JournalRoles.ROLE_CREATE_COMPLAINT.name());
        }

        private boolean existingProblem() {
            return getNetworkProblem().getId() != null;
        }

        public ComplaintCreateForm(String id, ClientComplaint problem){
            super(id, new CompoundPropertyModel(problem));
            if (problem.getClient() != null){
                problem.setEnteredClient(problem.getClient().getClientName());
            }
            initForm();
        }

        @Override
        protected void onSubmit() {
            ClientComplaint enteredProblem = getNetworkProblem();
            saveComplaint(enteredProblem);
            enteredProblem.setFailureDescription("");
            enteredProblem.setFailureTime(new java.util.Date());
        }

        private ClientComplaint getNetworkProblem() {
            return (ClientComplaint) getModelObject();
        }
    }
}