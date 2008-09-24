package net.paguo.web.wicket.bpm;

import net.paguo.web.wicket.SecuredWebPage;
import net.paguo.web.wicket.JournalApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.HeaderContributor;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmConfiguration;

/**
 * User: sreentenko
 * Date: 23.09.2008
 * Time: 0:45:33
 */
public abstract class BpmPage extends SecuredWebPage {
    private static final long serialVersionUID = -7198446174802636246L;

    @SpringBean(name = "jbpmTemplate")
    JbpmTemplate template;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public BpmPage(PageParameters parameters){
        super();
        getContext().setActorId(findSessionUser().getPermissionEntry().getUserName());
        add(HeaderContributor.forCss(JournalApplication.class, "wstyles.css"));
    }

    protected JbpmContext getContext() {
        final JbpmConfiguration jbpmConfiguration = getTemplate().getJbpmConfiguration();
        JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
        if (jbpmContext == null){
            jbpmContext = jbpmConfiguration.createJbpmContext();
        }
        return jbpmContext;
    }

    @Override
    protected void addLinks() {
        super.addLinks();
    }
}
