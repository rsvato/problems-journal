package net.paguo.web.wicket.bpm;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.behavior.IBehavior;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.jbpm.taskmgmt.exe.TaskInstance;
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
    private static final long serialVersionUID = 6835957528367707247L;

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

        List myTasks = getTemplate().findPooledTaskInstances(authenticatedUser.getUsername());

        add(new TaskListView("groupTasks", groupTasks));
        add(new TaskListView("myTasks", myTasks));
    }

    private class TaskListView extends ListView {
        private static final long serialVersionUID = -6976112108566087589L;

        public TaskListView(String s, List groupTasks) {
            super(s, groupTasks);
        }

        protected void populateItem(ListItem item) {
            TaskInstance instance = (TaskInstance) item.getModelObject();
            item.add(new Label("name", instance.getName()));
            item.add(new DateLabel("date", new PropertyModel(instance, "start"), new StyleDateConverter("S", true)));
        }
    }
}
