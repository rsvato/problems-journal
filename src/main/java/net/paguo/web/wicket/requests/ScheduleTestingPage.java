package net.paguo.web.wicket.requests;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.Testing;
import net.paguo.domain.testing.TestingPlan;
import net.paguo.web.wicket.SettingsAwarePage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValueConversionException;

import java.util.Date;

/**
 * @author Reyentenko
 */
public class ScheduleTestingPage extends SettingsAwarePage {
    private static final long serialVersionUID = -3408663244632188843L;

    private static final Log log = LogFactory.getLog(ScheduleTestingPage.class);

    @SpringBean
    private RequestController requestController;

    public RequestController getRequestController() {
        return requestController;
    }

    public void setRequestController(RequestController controller) {
        this.requestController = controller;
    }

    public ScheduleTestingPage(PageParameters parameters) {
        Request req;
        try {
            Integer requestId = parameters.getInt("request");
            req = getRequestController().get(requestId);
            if (req == null) {
                log.error("Request not found for id " + requestId);
                throw new RestartResponseException(Application.get().getHomePage());
            }
        } catch (StringValueConversionException exc) {
            log.error("No parameter or bad parameter");
            Session.get().error("Bad parameters");
            throw new RestartResponseException(Application.get().getHomePage());
        }

        if (req.getTestings().size() > getMaxTestingCount()) {
            Session.get().error("Maximum testing count exceeded");
            throw new RestartResponseException(Application.get().getHomePage());
        }

        final Request formObject = req;
        final TestingPlan plan = new TestingPlan();

        final Form form = new Form("testingPlanForm") {
            private static final long serialVersionUID = 3388281631924481218L;

            protected void onSubmit() {
                Testing t = new Testing();
                t.setCreationDate(new Date());
                t.setRequest(formObject);
                t.setPlan(plan);
                plan.setCreationDate(new Date());
                plan.setCreator(findSessionUser());
                formObject.getTestings().add(t);
                formObject.setCurrentStage(ProcessStage.SCHEDULETESTING);
                getRequestController().saveRequest(formObject);
                setRedirect(true);
                setResponsePage(ListRequestPage.class);
            }
        };
        add(form);
        SimpleClassPanel panel = new SimpleClassPanel("planPanel", plan);
        form.add(panel);
        add(new FeedbackPanel("feedback"));
        add(HeaderContributor.forCss(ScheduleTestingPage.class, "forms.css"));

    }
}
