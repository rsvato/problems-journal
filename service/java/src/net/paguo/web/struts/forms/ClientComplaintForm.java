package net.paguo.web.struts.forms;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: slava
 * Date: 02.10.2006
 * Time: 1:38:55
 * To change this template use File | Settings | File Templates.
 */
public class ClientComplaintForm extends ProblemForm{
    private Integer failureClient;

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
}
