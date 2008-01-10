package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 21.11.2007
 * Time: 0:55:33
 */
@AllowedRole("ROLE_CREATE_PROBLEM")
public class NetworkProblemCreatePage extends SecuredWebPage {
    private static final Log log = LogFactory.getLog(NetworkProblemCreatePage.class);

    @SpringBean
    private NetworkFailureController controller;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public NetworkProblemCreatePage() {
        add(new NetworkProblemCreateForm("form"));
        add(new FeedbackPanel("feedback"));
    }

    public NetworkProblemCreatePage(PageParameters parameters){
        int problemId = parameters.getInt("problemId");
        NetworkProblem problem = getController().getNetworkProblem(problemId);
        if (problem == null){
            info("Non-existent problem");
            problem = new NetworkProblem();
        }
        add(new FeedbackPanel("feedback"));
        add(new NetworkProblemCreateForm("form", problem));
    }

    void saveProblem(NetworkProblem problem){
        final NetworkFailureController failureController = getController();
        if (failureController == null){
            error("General error: cannot establish DB connection");
        } else {
            try {
                if (problem.getId() == null){
                    //fill user field only for new problems
                    problem.setUserCreated(findSessionUser());
                }
                failureController.saveCrash(problem);
                final String message = getLocalizer().getString("operation.successful", this);
                Session.get().info(message);
                setResponsePage(NetworkProblemsPage.class);
            } catch (ControllerException e) {
                log.error(e);
                //TODO: exctract message
                error("Unexpected error communicated with DB.");
            }
        }
    }

    public final class NetworkProblemCreateForm extends Form {

        public NetworkProblemCreateForm(String id) {
            super(id, new CompoundPropertyModel(new NetworkProblem()));
            getNetworkProblem().setFailureTime(new java.util.Date());
            initForm();
        }

        private void initForm() {
            add(new TextArea("failureDescription").setRequired(true));
            add(new DateTimeField("failureTime")
                    .setRequired(true));

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
        }

        private boolean existingProblem() {
            return getNetworkProblem().getId() != null;
        }

        public NetworkProblemCreateForm(String id, NetworkProblem problem){
            super(id, new CompoundPropertyModel(problem));
            initForm();
        }

        @Override
        protected void onSubmit() {
            NetworkProblem enteredProblem = getNetworkProblem();
            saveProblem(enteredProblem);
            enteredProblem.setFailureDescription("");
            enteredProblem.setFailureTime(new java.util.Date());
        }

        private NetworkProblem getNetworkProblem() {
            return (NetworkProblem) getModelObject();
        }
    }
}
