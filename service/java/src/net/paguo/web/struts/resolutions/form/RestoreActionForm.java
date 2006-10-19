package net.paguo.web.struts.resolutions.form;

import net.paguo.web.struts.forms.MultiactionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * User: slava
 * Date: 20.10.2006
 * Time: 0:21:06
 * Version: $Id$
 */
public class RestoreActionForm extends MultiactionForm {
    private static final Log log = LogFactory.getLog(RestoreActionForm.class);
    private String actionDescription;
    private String actionTime;


    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        log.debug("reset(...)");
        this.actionDescription = null;
        this.actionTime = null;
        super.reset(actionMapping, httpServletRequest);
    }
}
