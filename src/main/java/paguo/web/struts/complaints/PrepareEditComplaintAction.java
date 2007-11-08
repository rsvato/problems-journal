package net.paguo.web.struts.complaints;

import static org.apache.commons.beanutils.PropertyUtils.setSimpleProperty;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.web.struts.BaseFailureAndClientAction;
import net.paguo.web.struts.exceptions.NothingFoundException;
import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class PrepareEditComplaintAction  extends BaseFailureAndClientAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println(form);
        Integer id = (Integer) getSimpleProperty(form, "failureId");
        if (id != null && id > 0){
           ClientComplaint problem = getController().getComplaintDao().read(id);
            if (problem != null){
                setSimpleProperty(form, "failureId", problem.getId());
                setSimpleProperty(form, "failureDescription", problem.getFailureDescription());
                setSimpleProperty(form, "failureTime", PrepareEditComplaintAction.df.format(problem.getFailureTime()));
                setSimpleProperty(form, "failureClient", problem.getClient().getId());
                Collection<NetworkProblem> openProblems = getController().findOpenProblems();
                request.setAttribute("openProblems", openProblems);
            }else{
                throw new NothingFoundException();
            }

        }
        Collection<ClientItem> allClients = getClientController().getAllClients();
        request.setAttribute("clients", allClients);
        return mapping.findForward("next");
    }
}
