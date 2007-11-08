package net.paguo.web.struts.resolutions.action;

import org.apache.struts.action.*;
import net.paguo.web.struts.BaseFailureAction;
import net.paguo.web.struts.CrashKind;
import net.paguo.web.struts.exceptions.FailureClosedBeforeException;
import net.paguo.web.struts.exceptions.NothingFoundException;
import net.paguo.web.struts.resolutions.form.DiscoveredCauseForm;
import net.paguo.domain.problems.NetworkFailure;
import net.paguo.domain.problems.FailureRestore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * User: slava
 * Date: 09.10.2006
 * Time: 0:33:48
 * Version: $Id$
 */
public class AddCauseAction extends BaseFailureAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiscoveredCauseForm frm = (DiscoveredCauseForm) form;
        Integer failureId = frm.getFailureId();
        NetworkFailure failure = getController().getFailureDao().read(failureId);
        if (failure != null) {
            FailureRestore fr = failure.getRestoreAction();
            if (fr != null && fr.getCompleted()) {
                throw new FailureClosedBeforeException();
            }
            if (fr == null) {
                fr = new FailureRestore();
            }
            fr.setFailureCause(frm.getDiscoveredCause());
            fr.setRestoreAction(null);
            fr.setRestoreTime(null);
            fr.setCompleted(frm.isCloseFlag()); 
            failure.setRestoreAction(fr);
            
            getController().getFailureDao().update(failure);
            getController().closeDependedComplaints(fr, failure);
            return findForward(frm, mapping);
        } else {
            throw new NothingFoundException();
        }
    }

    private ActionForward findForward(DiscoveredCauseForm frm, ActionMapping mapping) {
        if (mapping.getParameter().equals("crash")){
            return mapping.findForward(NEXT);
        }else{
            return mapping.findForward("xnext");
        }
    }
}
