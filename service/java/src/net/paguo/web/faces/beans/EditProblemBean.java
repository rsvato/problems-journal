package net.paguo.web.faces.beans;

import net.paguo.domain.problems.NetworkProblem;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: slava
 * Date: 23.12.2006
 * Time: 23:54:41
 * Version: $Id$
 */
public class EditProblemBean implements NavigationConstants{
    private NetworkProblem problem;
    private NetworkFailureController controller;
    private static final Log log = LogFactory.getLog(EditProblemBean.class);

    public EditProblemBean(){
        this.problem = new NetworkProblem();
    }

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public NetworkProblem getProblem() {
        return problem;
    }

    public void setProblem(NetworkProblem problem) {
        this.problem = problem;
    }

    public String save(){
        try {
            getController().saveCrash(problem);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation successful", "Operation successful"));
            Utils.createValueBinding(EDIT_CRASH_PROBLEM, new NetworkProblem());
            CrashListBean.refresh();
        } catch (ControllerException e) {
            log.error(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return OUTCOME_LISTCRASHES;
    }

    public String cancel(){
        return OUTCOME_LISTCRASHES;
    }

    public boolean isCanDefine(){
        return problem.getRestoreAction() == null || ! problem.getRestoreAction().getCompleted();
    }
}
