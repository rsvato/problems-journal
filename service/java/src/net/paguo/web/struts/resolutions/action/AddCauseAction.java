package net.paguo.web.struts.resolutions.action;

import org.apache.struts.action.*;
import net.paguo.web.struts.BaseFailureAction;
import net.paguo.web.struts.CrashKind;
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
        ActionMessages messages = new ActionMessages();
        DiscoveredCauseForm frm = (DiscoveredCauseForm) form;
        Integer failureId = frm.getFailureId();
        NetworkFailure failure = getController().getFailureDao().read(failureId);
        if (failure != null) {
            FailureRestore fr = failure.getRestoreAction();
            if (fr != null && fr.getCompleted()) {
                messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.failure.resolved"));
                addErrors(request, messages);
                return mapping.findForward(ERROR);
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
            if (CrashKind.CRASH.equals(frm.getCrashKind())){
                return mapping.findForward(NEXT);
            }else{
                return mapping.findForward("xnext");
            }
        } else {
            messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.nothing.found"));
            return mapping.findForward(ERROR);
        }
    }
}
