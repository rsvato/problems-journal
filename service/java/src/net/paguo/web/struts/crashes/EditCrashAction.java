package net.paguo.web.struts.crashes;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.domain.problems.NetworkProblem;
import net.paguo.controller.NetworkFailureController;
import net.paguo.dao.NetworkProblemDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: slava
 * Date: 01.10.2006
 * Time: 3:25:12
 * To change this template use File | Settings | File Templates.
 */
public class EditCrashAction extends Action {
    private NetworkFailureController controller;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = (Integer) getSimpleProperty(form, "failureId");
        String description = (String) getSimpleProperty(form, "failureDescription");
        String failureTime = (String) getSimpleProperty(form, "failureTime");
        boolean newCrash = id == null || id == 0;
        ActionMessages messages = new ActionMessages();
        NetworkProblem problem;
        NetworkProblemDao problemDao = getController().getProblemDao();
        if (newCrash){
           problem = new NetworkProblem();
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

        Date failTime;

        try{
           failTime = df.parse(failureTime);
        }catch(ParseException e){
            messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.time"));
            addErrors(request, messages);
            saveErrors(request, messages);
            return mapping.getInputForward();
        }

        problem.setFailureTime(failTime);
        problem.setFailureDescription(description);

        if (newCrash){
            problemDao.create(problem);
        }else{
            problemDao.update(problem);
        }

        return mapping.findForward("success");

    }
}
