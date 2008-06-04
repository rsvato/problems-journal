package net.paguo.web.wicket.requests;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.Testing;
import net.paguo.domain.testing.TestingResults;
import net.paguo.web.wicket.SecuredWebPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
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

    public AddTestingResultsPage(PageParameters parameters) {
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

        final Form form = new Form("testingResultsForm") {
            private static final long serialVersionUID = 3388281631924481218L;

            protected void onSubmit() {
                Request r = getRequestController().get(formObject.getId());
                final Set<Testing> testings = r.getTestings();
                Testing toSet = null;
                int count = 0;
                for (Testing testing : testings) {
                    if (testing.getResult() == null) {
                        count++;
                        toSet = testing;
                    }
                }
                if (count > 1) {
                    Session.get().error("More than one testing plans found");
                    throw new RestartResponseException(Application.get().getHomePage());
                }

                if (toSet == null) {
                    Session.get().error("No acceptable testing plan found");
                    throw new RestartResponseException(Application.get().getHomePage());
                }
                
                results.setCreator(findSessionUser());
                results.setCreationDate(new Date());
                r.getTestings().remove(toSet);
                toSet.setResult(results);
                r.getTestings().add(toSet);
                ProcessStage nextStage = null;
                switch (results.getResult()) {
                    case TRSUCCESS:
                        nextStage = ProcessStage.BEFOREENABLING;
                        break;
                    case TRFAILURE:
                        nextStage = ProcessStage.OPENED;
                        break;
                    case TRRETRY:
                        nextStage = ProcessStage.BEFORETESTING;
                }
                r.setCurrentStage(nextStage);
                getRequestController().saveRequest(r);
                setResponsePage(ListRequestPage.class);
            }
        };
        add(form);
        SimpleClassPanel panel = new SimpleClassPanel("resultsPanel", results);
        form.add(panel);
        add(new FeedbackPanel("feedback"));
    }
}
