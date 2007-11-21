package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
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
public class NetworkProblemCreatePage extends SecuredWebPage {
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

    void saveProblem(NetworkProblem problem){
        final NetworkFailureController failureController = getController();
        if (failureController == null){
            error("General error: cannot establish DB connection");
        } else {
            failureController.createFailure(problem.getFailureDescription(),
                    problem.getFailureTime());
            final String message = getLocalizer().getString("operation.successful", this);
            Session.get().info(message);
            setResponsePage(NetworkProblemsPage.class);
        }
    }

    public final class NetworkProblemCreateForm extends Form {

        public NetworkProblemCreateForm(String id) {
            super(id, new CompoundPropertyModel(new NetworkProblem()));
            getNetworkProblem().setFailureTime(new java.util.Date());
            add(new TextArea("failureDescription"));
            add(new DateTimeField("failureTime")
                    .setRequired(true));
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
