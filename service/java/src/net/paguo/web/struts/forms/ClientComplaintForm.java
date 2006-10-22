package net.paguo.web.struts.forms;

import org.apache.struts.action.ActionMapping;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * User: slava
 * Date: 02.10.2006
 * Time: 1:38:55
 * Version: $Id$
 */
public class ClientComplaintForm extends ProblemForm{
    private Integer failureClient;
    private Integer parentProblem;

    public Integer getFailureClient() {
        return failureClient;
    }

    public void setFailureClient(Integer failureClient) {
        this.failureClient = failureClient;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        this.failureClient = null;
        super.reset(actionMapping, httpServletRequest); 
    }

    public Integer getParentProblem() {
        return parentProblem;
    }

    public void setParentProblem(Integer parentProblem) {
        this.parentProblem = parentProblem;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
