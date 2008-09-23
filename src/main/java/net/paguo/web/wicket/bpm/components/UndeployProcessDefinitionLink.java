package net.paguo.web.wicket.bpm.components;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.jbpm.JbpmContext;
import net.paguo.web.wicket.bpm.utils.ProcessUtils;


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
        final JbpmContext jbpmContext = ProcessUtils.getContext(getTemplate());
        jbpmContext.getGraphSession().deleteProcessDefinition(getProcessDefId());
        jbpmContext.close();
        setResponsePage(getPage().getClass());
    }

}
