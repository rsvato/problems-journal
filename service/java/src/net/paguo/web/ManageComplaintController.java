package net.paguo.web;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.ClientItemController;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.clients.ClientItem;
import net.paguo.web.utils.ClientItemPropertyEditor;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * @version $Id $
 */
public class ManageComplaintController extends SimpleFormController {
    private NetworkFailureController controller;
    private ClientItemController referenceController;
    private static final Log log = LogFactory.getLog(ManageComplaintController.class);


    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }


    public ClientItemController getReferenceController() {
        return referenceController;
    }

    public void setReferenceController(ClientItemController referenceController) {
        this.referenceController = referenceController;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), false));
        binder.registerCustomEditor(ClientItem.class, new ClientItemPropertyEditor(getReferenceController()));
        super.initBinder(request, binder);
    }

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        String logStr = "referenceData(): ";
        log.debug(logStr + "<<<");
        Map<String, Collection<ClientItem>> result = new HashMap<String, Collection<ClientItem>> ();
        String key = "clients";
        Collection<ClientItem> clients = getReferenceController().getAllClients();
        log.debug(logStr + " Got " + clients.size() + " reference records");
        result.put(key, clients);
        log.debug(logStr + "<<<");
        return result;
    }

    @Override
    protected ModelAndView onSubmit(Object command, BindException errors) throws Exception{
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
        ModelAndView result = new ModelAndView("list-complaints");
        List<ClientComplaint> model = getController().findAllComplaints();
        result.addObject("failures", model);
        return result;
    }

    @Override
    protected void doSubmitAction(Object command) throws Exception {
        String logPrefix = "doSubmitAction(): ";
        log.debug(logPrefix + "<<<");
        ClientComplaint problem = (ClientComplaint) command;
        log.debug(problem);
        if (problem.getId() == null)
            getController().getComplaintDao().create(problem);
        else
            getController().getComplaintDao().update(problem);
        log.debug(logPrefix + ">>>");
    }
}
