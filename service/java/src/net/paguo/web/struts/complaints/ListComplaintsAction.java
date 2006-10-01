package net.paguo.web.struts.complaints;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.ClientComplaint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: slava
 * Date: 01.10.2006
 * Time: 3:07:18
 * To change this template use File | Settings | File Templates.
 */
public class ListComplaintsAction extends Action{
    private NetworkFailureController controller;
    private static final String FAILURES_KEY = "failures";

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ClientComplaint> problems = getController().findAllComplaints();
        request.setAttribute(FAILURES_KEY, problems);
        return mapping.findForward("list");
    }
}
