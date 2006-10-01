package net.paguo.web.struts.crashes;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import static org.apache.commons.beanutils.PropertyUtils.setSimpleProperty;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.domain.problems.NetworkProblem;
import net.paguo.web.struts.BaseFailureAction;

public class PrepareEditCrashAction extends BaseFailureAction {


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println(form);
        Integer id = (Integer) getSimpleProperty(form, "failureId");
        if (id != null){
           NetworkProblem problem = getController().getProblemDao().read(id);
            if (problem != null){
                setSimpleProperty(form, "failureId", problem.getId());
                setSimpleProperty(form, "failureDescription", problem.getFailureDescription());
                setSimpleProperty(form, "failureTime", df.format(problem.getFailureTime()));
            }else{
                ActionMessages messages = new ActionMessages();
                messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.bad.identifier"));
                addErrors(request, messages);
                return mapping.findForward("error");
            }
        }
        return mapping.findForward("next");
    }
}
