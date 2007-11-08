package net.paguo.web.struts.forms;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * User: slava
 * Date: 02.10.2006
 * Time: 1:36:12
 * Version: $Id$
 */
public class ProblemForm extends SelectFailureForm{
    private String failureTime;
    private String failureDescription;

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }

    public String getFailureDescription() {
        return failureDescription;
    }

    public void setFailureDescription(String failureDescription) {
        this.failureDescription = failureDescription;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        this.failureTime = null;
        this.failureDescription = null;
        super.reset(actionMapping, httpServletRequest);
    }
}
