package net.paguo.web.struts.resolutions.action;

import net.paguo.web.struts.BaseFailureAction;
import net.paguo.web.struts.CrashKind;
import net.paguo.web.struts.exceptions.FailureClosedBeforeException;
import net.paguo.web.struts.exceptions.FailureNoCauseProvidedException;
import net.paguo.web.struts.exceptions.NothingFoundException;
import net.paguo.web.struts.exceptions.InvalidDateException;
import net.paguo.web.struts.resolutions.form.RestoreActionForm;
import net.paguo.domain.problems.NetworkFailure;
import net.paguo.domain.problems.FailureRestore;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.problems.ClientComplaint;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * User: slava
 * Date: 20.10.2006
 * Time: 0:27:36
 * Version: $Id$
 */
public class AddRestoreAction extends BaseFailureAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        String actionTime = (String) PropertyUtils.getProperty(form, "actionTime");
        String actionDescription = (String) PropertyUtils.getProperty(form, "actionDescription");
        Integer failureId = (Integer) PropertyUtils.getProperty(form, "failureId");
        CrashKind outcomeId = (CrashKind) PropertyUtils.getProperty(form, "crashKind");

        NetworkFailure failure = getController().getFailureDao().read(failureId);
        if (failure != null) {
            FailureRestore restore = failure.getRestoreAction();
            if (restore == null || restore.getFailureCause() == null) {
                throw new FailureNoCauseProvidedException();
            } else if (restore.getCompleted()) {
                throw new FailureClosedBeforeException();
            }
            Date closeTime;
            try {
                closeTime = df.parse(actionTime);
            } catch (ParseException e) {
                throw new InvalidDateException(e);
            }
            restore.setRestoreTime(closeTime);
            restore.setRestoreAction(actionDescription);
            restore.setCompleted(true);
            if (CrashKind.CRASH.equals(outcomeId)){
                System.err.println("This is crash");
                NetworkProblem problem = getController().getProblemDao().read(failure.getId());
                List<ClientComplaint> complaints = problem.getDependedComplaints();
                if (complaints != null){
                   for(ClientComplaint complaint : complaints){
                       System.err.println("Closing " + complaint.getId());
                       FailureRestore action = new FailureRestore();
                       action.setRestoreTime(closeTime);
                       action.setRestoreAction("RESOLVED BY PARENT");
                       action.setCompleted(true);
                       complaint.setRestoreAction(action);
                       getController().getComplaintDao().update(complaint);
                       System.err.println("Closed " + complaint.getId());
                   }
                }
            }
            getController().getFailureDao().update(failure);

            if (CrashKind.CRASH.equals(outcomeId)){
                return mapping.findForward(NEXT);
            }else{
                return mapping.findForward("xnext");
            }
        } else {
            throw new NothingFoundException();
        }

    }
}
