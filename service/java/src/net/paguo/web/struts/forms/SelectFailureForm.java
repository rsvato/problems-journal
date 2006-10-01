package net.paguo.web.struts.forms;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SelectFailureForm extends ValidatorForm {
    private Integer failureId;

    public Integer getFailureId() {
        return failureId;
    }

    public void setFailureId(Integer failureId) {
        this.failureId = failureId;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        this.failureId = null;
        super.reset(actionMapping, httpServletRequest);
    }
}
