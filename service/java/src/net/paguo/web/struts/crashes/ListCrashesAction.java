package net.paguo.web.struts.crashes;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.acegisecurity.annotation.Secured;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.web.struts.BaseFailureAction;
import net.paguo.utils.NetworkFailureComparator;

import java.util.List;
import java.util.Collections;

/**
 * User: slava
 * Date: 01.10.2006
 * Time: 2:21:53
 * Version: $Id$
 */
@Secured("ROLE_ALL")
public class ListCrashesAction extends BaseFailureAction {
    private static final String FAILURES_KEY = "failures";

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<NetworkProblem> problems = getController().findAllProblems();
        Collections.sort(problems, new NetworkFailureComparator());
        request.setAttribute(FAILURES_KEY, problems);
        return mapping.findForward("list");
    }
}
