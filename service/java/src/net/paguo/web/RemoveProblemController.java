package net.paguo.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paguo.domain.problems.NetworkProblem;

/**
 * @version $Id $
 */
public class RemoveProblemController extends ListProblemsController{
    private static Log log = LogFactory.getLog(RemoveProblemController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logStr = "handleRequestInternal(...): ";
        log.debug(logStr + "<<<");
        String id = request.getParameter("problemId").trim();
        if (StringUtils.isNotEmpty(id)){
            try {
                Integer complaintId = Integer.decode(id);
                NetworkProblem problem = getController().getProblemDao().read(complaintId);
                if (null != problem) {
                    log.debug(logStr + " entity found. Will delete");
                    getController().getProblemDao().delete(problem);
                }
            } catch (NumberFormatException e) {
                log.error(logStr + " bad parameter value " + id);
            }

        }
        log.debug(logStr + ">>>");
        return super.handleRequestInternal(request, response);
    }
}
