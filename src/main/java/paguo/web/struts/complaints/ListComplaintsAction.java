package net.paguo.web.struts.complaints;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.web.struts.BaseFailureAction;
import net.paguo.utils.NetworkFailureComparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * User: slava
 * Date: 01.10.2006
 * Time: 3:07:18
 * To change this template use File | Settings | File Templates.
 */
public class ListComplaintsAction extends BaseFailureAction {
    private static final String FAILURES_KEY = "failures";

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ClientComplaint> problems = getController().findAllComplaints();
        Collections.sort(problems, new NetworkFailureComparator());
        request.setAttribute(FAILURES_KEY, problems);
        return mapping.findForward("list");
    }
}
