package net.paguo.web.wicket.bpm;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import net.paguo.web.wicket.auth.UserView;
import net.paguo.web.wicket.JournalWebSession;

import java.util.List;

/**
 * User: sreentenko
 * Date: 24.09.2008
 * Time: 1:50:15
 */
public class MyTasksPanel extends Panel {

    @SpringBean(name = "jbpmTemplate")
    JbpmTemplate template;

    public JbpmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JbpmTemplate template) {
        this.template = template;
    }

    public MyTasksPanel(String id) {
        super(id);

        final UserView authenticatedUser = JournalWebSession.get().getAuthenticatedUser();
        List groupTasks = getTemplate().findPooledTaskInstances(authenticatedUser.getUsername());
    }
}
