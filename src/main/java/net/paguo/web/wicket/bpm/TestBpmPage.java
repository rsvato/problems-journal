package net.paguo.web.wicket.bpm;

import org.apache.wicket.PageParameters;

/**
 * User: sreentenko
 * Date: 23.09.2008
 * Time: 0:58:11
 */
public class TestBpmPage extends BpmPage{
    private static final long serialVersionUID = -8854539577765559881L;

    public TestBpmPage(PageParameters parameters) {
        super(parameters);
        add(new ProcessDefinitionsPanel("defs", getContext()));
        add(new MyTasksPanel("tasks"));
    }
}
