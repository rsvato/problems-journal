package net.paguo.web.struts;

import net.paguo.web.struts.BaseFailureAction;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.generic.dao.GenericDao;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DropFailureAction extends BaseFailureAction {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer failureId = (Integer) PropertyUtils.getSimpleProperty(form, "failureId");
        String discriminator = mapping.getParameter();
        ActionMessages messages = new ActionMessages();
        if ("crash".equals(discriminator)){
            return doRemove(failureId, mapping, request, getController().getProblemDao());
        }else if ("complaint".equals(discriminator)){
            return doRemove(failureId, mapping, request, getController().getComplaintDao());
        }
        messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.unknown.route"));
        return mapping.getInputForward();
    }

    @SuppressWarnings("unchecked")
    <T extends GenericDao> ActionForward doRemove(Integer failureId, ActionMapping mapping, HttpServletRequest request, T dao){
        Object problem = dao.read(failureId);
        ActionMessages messages = new ActionMessages();
        if (problem == null){
            messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.id"));
            addErrors(request, messages);
            saveErrors(request, messages);
            return mapping.getInputForward();
        }
        dao.delete(problem);
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("operation.successful"));
        return mapping.findForward("success");
    }
}
