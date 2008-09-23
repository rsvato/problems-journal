package net.paguo.web.wicket.bpm.components;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmConfiguration;


/**
 * @author Svyatoslav Reyentenko
 */
public class UndeployProcessDefinitionLink extends Link {
    private Long processDefId;

    @SpringBean(name = "jbpmTemplate")
    JbpmTemplate template;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public Long getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(Long processDefId) {
        this.processDefId = processDefId;
    }

    public UndeployProcessDefinitionLink(String id, Long processDefinitionId) {
        super(id);
        this.processDefId = processDefinitionId;
    }

    public void onClick() {
        getContext(getTemplate()).getGraphSession().deleteProcessDefinition(getProcessDefId());
        setResponsePage(getPage());
    }

    protected JbpmContext getContext(JbpmTemplate template) {
        final JbpmConfiguration jbpmConfiguration = template.getJbpmConfiguration();
        JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
        if (jbpmContext == null){
            jbpmContext = jbpmConfiguration.createJbpmContext();
        }
        return jbpmContext;
    }
}
