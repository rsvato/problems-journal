package net.paguo.web.struts.complaints;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.PropertyUtils;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.ClientItemController;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.clients.ClientItem;
import net.paguo.web.struts.crashes.PrepareEditCrashAction;
import net.paguo.web.struts.BaseFailureAndClientAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class PrepareEditComplaintAction  extends BaseFailureAndClientAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println(form);
        Integer id = (Integer) getSimpleProperty(form, "failureId");
        if (id != null){
           ClientComplaint problem = getController().getComplaintDao().read(id);
            if (problem != null){
                PropertyUtils.setSimpleProperty(form, "failureId", problem.getId());
                PropertyUtils.setSimpleProperty(form, "failureDescription", problem.getFailureDescription());
                PropertyUtils.setSimpleProperty(form, "failureTime", PrepareEditComplaintAction.df.format(problem.getFailureTime()));
                PropertyUtils.setSimpleProperty(form, "failureClient", problem.getClient().getId());
            }else{
                ActionMessages messages = new ActionMessages();
                messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.bad.identifier"));
                addErrors(request, messages);
                return mapping.findForward("error");
            }
        }
        Collection<ClientItem> allClients = getClientController().getAllClients();
        request.setAttribute("clients", allClients);
        return mapping.findForward("next");
    }
}
