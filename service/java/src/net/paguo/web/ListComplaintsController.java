package net.paguo.web;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import net.paguo.controller.NetworkFailureController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 27.08.2006 23:09:21
 */
public class ListComplaintsController extends AbstractController {
    private NetworkFailureController controller;
    private static final Log log = LogFactory.getLog(ListComplaintsController.class);

    public ListComplaintsController(){
        setSupportedMethods(new String[] {METHOD_GET});
    }

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List model;
        ModelAndView result = new ModelAndView("list-complaints");
        model = getController().findAllComplaints();
        result.addObject("failures", model);
        return result;
    }

    public static final Integer CLIENT_REQUEST=1;
    enum ReqType {CLIENT, NETWORK};
}
