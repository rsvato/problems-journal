package net.paguo.web.struts.complaints;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import org.apache.struts.action.*;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.ClientItemController;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.clients.ClientItem;
import net.paguo.dao.ClientComplaintDao;
import net.paguo.web.struts.BaseFailureAndClientAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * User: slava
 * Date: 01.10.2006
 * Time: 3:25:12
 * Version: $Id$
 */
public class EditComplaintAction extends BaseFailureAndClientAction {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = (Integer) getSimpleProperty(form, "failureId");
        String description = (String) getSimpleProperty(form, "failureDescription");
        String failureTime = (String) getSimpleProperty(form, "failureTime");
        Integer clientId = (Integer) getSimpleProperty(form, "failureClient");
        Integer parentProblem = (Integer) getSimpleProperty(form, "parentProblem");
        boolean newComplaint = id == null || id == 0;
        ActionMessages messages = new ActionMessages();
        ClientComplaint problem;
        ClientComplaintDao problemDao = getController().getComplaintDao();
        if (newComplaint){
           problem = new ClientComplaint();
        } else {
            try{
                problem = problemDao.read(id);
            }catch(Exception e){
                problem = null;
            }
        }

        if (problem == null){
            messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.id"));
            addErrors(request, messages);
            saveErrors(request, messages);
            return mapping.getInputForward();
        }

        if (clientId == null){
           messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.id"));
           addErrors(request, messages);
           saveErrors(request, messages);
           return mapping.getInputForward();
        }

        ClientItem item = getClientController().getClientDao().read(clientId);

        if (item == null){
           messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.id"));
           addErrors(request, messages);
           saveErrors(request, messages);
           return mapping.getInputForward();
        }

        Date failTime;

        try{
           failTime = EditComplaintAction.df.parse(failureTime);
        }catch(ParseException e){
            messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.time"));
            addErrors(request, messages);
            saveErrors(request, messages);
            return mapping.getInputForward();
        }

        problem.setFailureTime(failTime);
        problem.setFailureDescription(description);
        problem.setClient(item);

        if (newComplaint){
            problemDao.create(problem);
        }else{

            if (parentProblem != null){
                NetworkProblem parent = getController().getProblemDao().read(parentProblem);
                problem.setParent(parent);
            }

            problemDao.update(problem);
        }

        form.reset(mapping, request);

        return mapping.findForward("success");

    }
}
