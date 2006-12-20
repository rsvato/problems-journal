package net.paguo.web.faces.beans;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkFailure;
import net.paguo.domain.problems.NetworkProblem;

import javax.faces.component.UIData;
import java.util.Collection;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.web.jsf.FacesContextUtils;

/**
 * User: slava
 * Date: 20.12.2006
 * Time: 0:00:07
 * Version: $Id$
 */
public class CrashListBean extends PaginableBean {
    private NetworkFailureController failureController;
    private Collection<NetworkProblem> failures;
    private NetworkFailure selectedFailure;
    private Integer currentPosition = 0;


    public NetworkFailure getSelectedFailure() {
        return selectedFailure;
    }

    public void setSelectedFailure(NetworkFailure selectedFailure) {
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
        if (failures == null){
            failures = getFailureController().findAllProblems();
            Collections.sort((List<NetworkProblem>) failures, new Comparator<NetworkProblem>(){
                public int compare(NetworkProblem o1, NetworkProblem o2) {
                    return o1.getFailureTime().compareTo(o2.getFailureTime());
                }
            }
            );
        }
        return failures;
    }

    public void setFailures(Collection failures) {
        this.failures = failures;
    }


    public void prepareEdit(){
        NetworkProblem failure = (NetworkProblem) itemsTable.getRowData();
        //reload (session was lost)
        failure = getFailureController().getProblemDao().read(failure.getId());
        System.err.println(failure.toString());
    }


}
