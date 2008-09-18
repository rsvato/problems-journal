package net.paguo.controller.bpm;

import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ProcessInstance;

import java.util.List;

/**
 * User: sreentenko
 * Date: 19.09.2008
 * Time: 0:08:59
 */
public class SimpleWorkflowController {

    private JbpmTemplate template;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public void startProcess(){
        getTemplate().execute(new JbpmCallback() {
            public Object doInJbpm(JbpmContext context) throws JbpmException {
                final ProcessInstance processInstance = new ProcessInstance(getTemplate().getProcessDefinition());
                processInstance.signal();
                context.save(processInstance);
                return null;
            }
        });
    }

    public List findRunningProcesses(){
        return (List) getTemplate().execute(new JbpmCallback() {
            public Object doInJbpm(JbpmContext context) throws JbpmException {
                final List list = context.getTaskList("actor");
                return list;
            }
        });
    }
}
