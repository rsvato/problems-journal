package net.paguo.web.wicket.bpm.components;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import net.paguo.web.wicket.bpm.utils.ProcessUtils;
import net.paguo.web.wicket.JournalWebSession;
import net.paguo.web.wicket.auth.UserView;

/**
 * User: sreentenko
 * Date: 24.09.2008
 * Time: 1:31:16
 */
public class StartProcessLink extends Link {
    private Long pid;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @SpringBean(name = "jbpmTemplate")
    JbpmTemplate template;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public StartProcessLink(String id, Long processDefinitionId) {
        super(id);
        this.pid = processDefinitionId;
    }

    public void onClick() {
        final JbpmContext jbpmContext = ProcessUtils.getContext(getTemplate());
        ProcessDefinition processDefinition = jbpmContext.getGraphSession()
                .getProcessDefinition(getPid());
        final UserView authenticatedUser = JournalWebSession.get().getAuthenticatedUser();
        jbpmContext.setActorId(authenticatedUser.getUsername());

        ProcessInstance pi = processDefinition.createProcessInstance();
        final TaskInstance taskInstance = pi.getTaskMgmtInstance().createStartTaskInstance();

        if (taskInstance != null){
            taskInstance.start(authenticatedUser.getUsername());
            jbpmContext.save(taskInstance);
        }else{
            pi.getRootToken().signal();
        }
        jbpmContext.save(pi);
        jbpmContext.close();
        setResponsePage(getPage().getClass());
    }
}
