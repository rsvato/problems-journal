package net.paguo.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.paguo.controller.NetworkFailureController;

import java.util.List;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 27.08.2006 23:09:21
 */
public class ListProblemsController extends AbstractController {
    private NetworkFailureController controller;
    private static final Log log = LogFactory.getLog(ListProblemsController.class);

    public ListProblemsController(){
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
        List model = null;
        log.info(ReqType.CLIENT);
        ModelAndView result = new ModelAndView("list-failures");
        model = getController().findAllProblems();
        result.addObject("failures", model);
        return result;
    }

    public static final Integer CLIENT_REQUEST=1;
    enum ReqType {CLIENT, NETWORK};
}
