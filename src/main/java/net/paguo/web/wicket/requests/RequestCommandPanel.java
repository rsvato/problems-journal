package net.paguo.web.wicket.requests;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 14.05.2008
 * Time: 1:04:21
 */
public class RequestCommandPanel extends Panel {
    private static final long serialVersionUID = 4458966794962857144L;

    @SpringBean
    private RequestController controller;

    public RequestController getController() {
        return controller;
    }

    public void setController(RequestController controller) {
        this.controller = controller;
    }

    public RequestCommandPanel(String id, final Request request) {
        super(id);
        PageParameters parameters = new PageParameters();
        final Integer requestId = request.getId();
        parameters.add("request", String.valueOf(requestId));

        add(new BookmarkablePageLink("edit", CreateRequestPage.class, parameters));

        Link promoteToTest = new BookmarkablePageLink("promoteToTest", PromoteToTestingPlan.class,
                parameters);
        final boolean requestOpened = ProcessStage.OPENED == request.getCurrentStage();
        promoteToTest.setVisible(requestOpened);
        add(promoteToTest);

        Link cancelByUser = new Link("cancelByUser") {
            private static final long serialVersionUID = 7362728226767093883L;

            public void onClick() {
                request.setCurrentStage(ProcessStage.CLIENTCANCEL);
                controller.saveRequest(request);
                setResponsePage(ListRequestPage.class);
            }
        };

        cancelByUser.setVisible(requestOpened);
        add(cancelByUser);

        Link scheduleLink = new BookmarkablePageLink("scheduleLink", ScheduleTestingPage.class,
                parameters);
        final boolean readyToScheduleTest = ProcessStage.BEFORETESTING == request.getCurrentStage();
        scheduleLink.setVisible(readyToScheduleTest);
        add(scheduleLink);
    }
}
