package net.paguo.controller.bpm;

import org.springframework.test.AbstractSpringContextTests;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.List;

/**
 * User: sreentenko
 * Date: 19.09.2008
 * Time: 0:13:16
 */
public class SimpleWorkflowControllerTest extends AbstractDependencyInjectionSpringContextTests {
    SimpleWorkflowController controller;

    public SimpleWorkflowController getController() {
        return controller;
    }

    public void setController(SimpleWorkflowController controller) {
        this.controller = controller;
    }

    public SimpleWorkflowControllerTest(){
        super();
        setAutowireMode(AUTOWIRE_BY_TYPE);
    }
    public void testControllerStart(){
        getController().startProcess();
    }

    public void testTaskList(){
        final List list = getController().findRunningProcesses();
        for (Object o : list) {
            System.out.println(String.valueOf(o));
            TaskInstance ti = (TaskInstance) o;
            System.out.println(ti.getName() + " " + ti.getActorId());

        }
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:WEB-INF/ds-context.xml"};
    }
}
