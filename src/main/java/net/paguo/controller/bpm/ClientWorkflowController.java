package net.paguo.controller.bpm;

import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import java.util.List;

/**
 * User: sreentenko
 * Date: 21.09.2008
 * Time: 1:23:27
 */
public class ClientWorkflowController {
    private JbpmTemplate template;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public List<ProcessInstance> findProcessInstances(){
        return getTemplate().findProcessInstances();
    }

    public ProcessInstance getNewProcessInstance() {
        ProcessInstance pi = this.template.getProcessDefinition().createProcessInstance();
        Token token = pi.getRootToken();
        token.signal();
        this.template.saveProcessInstance(pi);
        return pi;
    }

    public Long saveProcessInstance(ProcessInstance pi) {
        return this.template.saveProcessInstance(pi);
    }

    public ProcessInstance findProcessInstance(Long instanceId) {
        ProcessInstance pi = template.findProcessInstance(instanceId);
        return pi;

    }

    public void advanceProcessIntance(final Long instanceId) {
        getTemplate().execute(new JbpmCallback() {
            public Object doInJbpm(JbpmContext context) throws JbpmException {
                final ProcessInstance processInstance
                        = getTemplate().findProcessInstance(instanceId);
                processInstance.signal();
                context.save(processInstance);
                return null;
            }
        });
    }

    public void advanceProcessIntanceToTransition(Long instanceId, String transitionName) {
        ProcessInstance pi = template.findProcessInstance(instanceId);
        pi.signal(transitionName);
        template.saveProcessInstance(pi);
    }

    public void saveVariable(Long instanceId, String key, Object value) {
        ProcessInstance pi = template.findProcessInstance(instanceId);
        this.saveCtxVariable(pi, key, value);
    }

    private void saveCtxVariable(ProcessInstance pi, String key, Object value) {
        ContextInstance ctxI = pi.getContextInstance();
        ctxI.setVariable(key, value);
        template.saveProcessInstance(pi);
    }

    public void saveVariable(ProcessInstance pi, String key, Object value) {
        this.saveCtxVariable(pi, key, value);
    }

    public Object fetchVariableByKey(Long instanceId,String key) {
        ProcessInstance pi = template.findProcessInstance(instanceId);
        ContextInstance ctxI = pi.getContextInstance();
        return ctxI.getVariable(key);
    }
}
