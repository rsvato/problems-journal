package net.paguo.web.struts.crashes;

import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import static org.apache.commons.beanutils.PropertyUtils.setSimpleProperty;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PrepareEditCrashAction extends Action {
    private NetworkFailureController controller;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println(form);
        Integer id = (Integer) getSimpleProperty(form, "failureId");
        if (id != null){
           NetworkProblem problem = controller.getProblemDao().read(id);
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
