package net.paguo.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.ClientComplaint;

import java.util.List;

/**
 * @version $Id $
 */
public class RemoveComplaintController extends ListComplaintsController{
    private static Log log = LogFactory.getLog(RemoveComplaintController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logStr = "handleRequestInternal(...): ";
        log.debug(logStr + "<<<");
        String id = request.getParameter("complaintId").trim();
        if (StringUtils.isNotEmpty(id)){
            try {
                Integer complaintId = Integer.decode(id);
                ClientComplaint complaint = getController().getComplaintDao().read(complaintId);
                if (null != complaint) {
                    log.debug(logStr + " entity found. Will delete");
                    getController().getComplaintDao().delete(complaint);
                }
            } catch (NumberFormatException e) {
                log.error(logStr + " bad parameter value " + id);
            }

        }
        log.debug(logStr + ">>>");
        return super.handleRequestInternal(request, response);
    }
}
