package net.paguo.web.wicket.bpm;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import net.paguo.web.wicket.auth.UserView;
import net.paguo.web.wicket.JournalWebSession;

/**
 * User: sreentenko
 * Date: 25.09.2008
 * Time: 1:55:45
 */
public class RunningProcessesPanel extends Panel {

    @SpringBean(name = "jbpmTemplate")
    JbpmTemplate template;
    private static final long serialVersionUID = 382747407109569979L;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public RunningProcessesPanel(String id) {
        super(id);
        final UserView authenticatedUser = JournalWebSession.get().getAuthenticatedUser();

        getTemplate().findProcessInstances();
    }
}
