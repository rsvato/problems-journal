package net.paguo.web.faces.beans;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: slava
 * Date: 20.12.2006
 * Time: 0:00:07
 * Version: $Id$
 */
public class CrashListBean extends PaginableBean implements NavigationConstants{
    private NetworkFailureController failureController;
    private Collection<NetworkProblem> failures;
    private NetworkProblem selectedFailure;
    private Integer currentPosition = 0;


    public NetworkProblem getSelectedFailure() {
        return selectedFailure;
    }

    public void setSelectedFailure(NetworkProblem selectedFailure) {
        this.selectedFailure = selectedFailure;
    }

    public Integer getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }

    public NetworkFailureController getFailureController() {
        return failureController;
    }

    public void setFailureController(NetworkFailureController failureController) {
        this.failureController = failureController;
    }


    public Collection getFailures() {
        failures = getFailureController().findAllProblems();
        Collections.sort((List<NetworkProblem>) failures, new Comparator<NetworkProblem>(){
            public int compare(NetworkProblem o1, NetworkProblem o2) {
                return o1.getFailureTime().compareTo(o2.getFailureTime());
            }
        }
        );
        return failures;
    }

    public void setFailures(Collection<NetworkProblem> failures) {
        this.failures = failures;
    }


    public String prepareEdit(){
        NetworkProblem failure = (NetworkProblem) itemsTable.getRowData();
        //reload (session was lost)
        failure = getFailureController().getProblemDao().read(failure.getId());
        System.err.println(failure.toString());
        Utils.bindObjectToVar(NavigationConstants.EDIT_CRASH_PROBLEM, failure);
        return NavigationConstants.EDITCRASH_OUTCOME;
    }

    public String createNew(){
        Utils.bindObjectToVar(EDIT_CRASH_PROBLEM, new NetworkProblem());
        return EDITCRASH_OUTCOME;
    }

    public void sort(){

    }


}
