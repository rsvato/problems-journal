package net.paguo.web;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.controller.NetworkFailureController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * @version $Id $
 */
public class ManageFailureController extends SimpleFormController {
    private NetworkFailureController controller;
    private static final Log log = LogFactory.getLog(ManageFailureController.class);


    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), false));
        super.initBinder(request, binder);
    }

    @Override
    protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
        if (! errors.getAllErrors().isEmpty()){
            return new ModelAndView(getFormView(), errors.getModel());
        }
        try {
            super.onSubmit(command);
        } catch (Exception e) {
            return new ModelAndView(super.getFormView());
        }
        return createDefaultMAV();
    }

    private ModelAndView createDefaultMAV() {
        ModelAndView result = new ModelAndView("list-failures");
        List<NetworkProblem> model = getController().findAllProblems();
        result.addObject("failures", model);
        return result;
    }

    @Override
    protected void doSubmitAction(Object command) throws Exception {
        String logPrefix = "doSubmitAction(): ";
        log.debug(logPrefix + "<<<");
        NetworkProblem problem = (NetworkProblem) command;
        log.debug(problem);
        if (problem.getId() == null)
            getController().getProblemDao().create(problem);
        else
            getController().getProblemDao().update(problem);
        log.debug(logPrefix + ">>>");
    }
}
