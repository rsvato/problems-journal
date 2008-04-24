package net.paguo.web.wicket.requests;

import net.paguo.web.wicket.SecuredWebPage;
import net.paguo.controller.request.RequestController;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Reyentenko
 */
public class ListRequestPage extends SecuredWebPage {
    private static final long serialVersionUID = 6601552429928651832L;

    @SpringBean
    private RequestController controller;

    public RequestController getController() {
        return controller;
    }

    public void setController(RequestController controller) {
        this.controller = controller;
    }

    public ListRequestPage(){
        getController().getRequestDao()
    }
}
