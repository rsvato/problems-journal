package net.paguo.web.struts.crashes;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: slava
 * Date: 01.10.2006
 * Time: 2:21:53
 * To change this template use File | Settings | File Templates.
 */
public class ListCrashesAction extends Action {
    private NetworkFailureController controller;
    private static final String FAILURES_KEY = "failures";

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<NetworkProblem> problems = getController().findAllProblems();
        request.setAttribute(FAILURES_KEY, problems);
        return mapping.findForward("list");
    }
}
