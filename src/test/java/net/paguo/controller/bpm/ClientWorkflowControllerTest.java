package net.paguo.controller.bpm;

import org.springframework.test.AbstractSpringContextTests;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.AbstractTransactionalSpringContextTests;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.graph.exe.ProcessInstance;

import java.util.List;

/**
 * User: sreentenko
 * Date: 19.09.2008
 * Time: 0:13:16
 */
public class ClientWorkflowControllerTest extends AbstractTransactionalSpringContextTests {
    ClientWorkflowController controller;

    public ClientWorkflowController getController() {
        return controller;
    }

    public void setController(ClientWorkflowController controller) {
        this.controller = controller;
    }

    public ClientWorkflowControllerTest(){
        super();
        setAutowireMode(AUTOWIRE_BY_TYPE);
        setDefaultRollback(false);
    }



    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:WEB-INF/ds-context.xml"};
    }

    public void testCreateInstance(){
        final ProcessInstance processInstance = getController().getNewProcessInstance();
        Long pid = getController().saveProcessInstance(processInstance);
        getController().advanceProcessIntance(pid);
        getController().advanceProcessIntance(pid);

    }

    public void testFindInstances(){
        final List<ProcessInstance> list = getController().findProcessInstances();
        System.out.println(list.size());
        for (ProcessInstance processInstance : list) {
            System.out.print(processInstance.getRootToken().getNode() + " ");
            System.out.println(processInstance.getId());
        }
    }
}