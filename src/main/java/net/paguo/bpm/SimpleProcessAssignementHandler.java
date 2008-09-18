package net.paguo.bpm;

import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * User: sreentenko
 * Date: 18.09.2008
 * Time: 23:47:25
 */
public class SimpleProcessAssignementHandler implements AssignmentHandler {
    public void assign(Assignable assignable, ExecutionContext executionContext) throws Exception {
        assignable.setActorId("actor");
        assignable.setPooledActors(new String[]{"actor", "bcd", "def"});
    }
}
