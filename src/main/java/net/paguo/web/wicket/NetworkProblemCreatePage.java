package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.problems.NetworkProblem;
import static net.paguo.domain.users.ApplicationRole.Action.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.util.string.StringValueConversionException;
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
    
    public NetworkProblemCreatePage(PageParameters parameters){
        NetworkProblem problem = new NetworkProblem();
        try {
            int problemId = parameters.getInt("problemId");
            problem = getController().getNetworkProblem(problemId);
            if (problem == null){
                info("Non-existent problem");
            }
        } catch (StringValueConversionException e) {
            log.debug("No parameters or bad parameters");
        }
        add(new FeedbackPanel("feedback"));
        final NetworkProblemCreateForm form = new NetworkProblemCreateForm("form", problem);
        if (problem.getId() != null){
            secureElement(form, NetworkProblem.class, CHANGE, OVERRIDE);
        }else{
            secureElement(form, NetworkProblem.class, CREATE);
        }
        add(form);
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

            if (existingProblem() && getNetworkProblem().getRestoreAction() != null &&
                    getNetworkProblem().getRestoreAction().getCompleted()){
                secureElement(p, NetworkProblem.class, OVERRIDE);
            }else{
                secureElement(p, NetworkProblem.class, CHANGE, OVERRIDE);
            }

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
