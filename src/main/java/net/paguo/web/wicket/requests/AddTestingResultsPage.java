package net.paguo.web.wicket.requests;

import net.paguo.web.wicket.SecuredWebPage;
import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.string.StringValueConversionException;

import java.util.Date;
import java.util.Set;

/**
 * @author Reyentenko
 */
public class AddTestingResultsPage extends SecuredWebPage {


    private static final Log log = LogFactory.getLog(AddTestingResultsPage.class);

    @SpringBean
    private RequestController requestController;
    private static final long serialVersionUID = -3718664741035820206L;

    public RequestController getRequestController() {
        return requestController;
    }

    public void setRequestController(RequestController controller) {
        this.requestController = controller;
    }
    public AddTestingResultsPage(PageParameters parameters){
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

        final Request formObject = req;
        final TestingResults results = new TestingResults();

        final Form form = new Form("testingPlanForm") {
            private static final long serialVersionUID = 3388281631924481218L;

            protected void onSubmit() {
                final Set<Testing> testings = formObject.getTestings();
                Testing toSet = null;
                int count = 0;
                for (Testing testing : testings) {
                    if (testing.getResult() == null){
                       count++;
                       toSet = testing;
                    }
                }
                if (count > 1){
                    Session.get().error("More than one testing plans found");
                    throw new RestartResponseException(Application.get().getHomePage());
                }

                if (toSet == null){
                    Session.get().error("No acceptable testing plan found");
                    throw new RestartResponseException(Application.get().getHomePage());
                }

                formObject.getTestings().remove(toSet);
                toSet.setResult(results);
                formObject.getTestings().add(toSet);
                ProcessStage nextStage = null;
                switch (results.getResult()){
                    case  SUCCESS:
                        nextStage = ProcessStage.BEFOREENABLING;
                        break;
                    case FAILURE:
                        nextStage = ProcessStage.OPENED;
                        break;
                    case RETRY:
                        nextStage = ProcessStage.BEFORETESTING;
                }
                formObject.setCurrentStage(nextStage);
                getRequestController().saveRequest(formObject);
            }
        };
        add(form);
        SimpleClassPanel panel = new SimpleClassPanel("planPanel", results);
        form.add(panel);
        add(new FeedbackPanel("feedback"));
    }
}
