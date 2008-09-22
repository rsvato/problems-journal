package net.paguo.web.wicket.bpm;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import java.util.List;

/**
 * User: sreentenko
 * Date: 23.09.2008
 * Time: 0:52:38
 */
public class ProcessDefinitionsPanel extends Panel {
    public ProcessDefinitionsPanel(String id, final JbpmContext context) {
        super(id);
        @SuppressWarnings("unchecked")
        List<ProcessDefinition> defs = context.getGraphSession().findAllProcessDefinitions();
        add(new ListView("definitions", defs){
            protected void populateItem(ListItem item) {
                final ProcessDefinition def = (ProcessDefinition) item.getModelObject();
                item.add(new Label("defLabel", def.getName()));
                item.add(new Label("version", String.valueOf(def.getVersion())));
                item.add(new Link("undeploy"){
                    private static final long serialVersionUID = 2920540899613040013L;

                    public void onClick() {
                        context.getGraphSession().deleteProcessDefinition(def.getId());
                    }
                });
            }
        });
    }
}
