package net.paguo.web.wicket.requests;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.RequiredService;
import net.paguo.web.wicket.SecuredWebPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValueConversionException;

/**
 * User: sreentenko
 * Date: 23.04.2008
 * Time: 23:33:59
 */
public class PromoteToTestingPlan extends SecuredWebPage {
    private static final long serialVersionUID = -3408663244632188843L;

    private static final Log log = LogFactory.getLog(PromoteToTestingPlan.class);

    @SpringBean
    private RequestController requestController;

    public RequestController getRequestController() {
        return requestController;
    }

    public void setRequestController(RequestController controller) {
        this.requestController = controller;
    }

    public PromoteToTestingPlan(PageParameters parameters) {
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

        final Request request = req;
        Form promoteForm = new Form("promoteForm") {
            private static final long serialVersionUID = -401324709572888867L;

            @Override
            protected void onSubmit() {
                request.setCurrentStage(ProcessStage.BEFORETESTING);
                getRequestController().saveRequest(request);
            }
        };
        if (request.getService() == null) {
            request.setService(new RequiredService());
        }
        final SimpleClassPanel child = new SimpleClassPanel("service", request.getService());
        child.setEnabled(ProcessStage.OPENED == request.getCurrentStage());
        promoteForm.add(child);
        add(promoteForm);
    }

}
