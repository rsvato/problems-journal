package net.paguo.controller;

import net.paguo.domain.problems.NetworkFailure;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.problems.FailureRestore;
import net.paguo.domain.clients.ClientItem;
import net.paguo.dao.NetworkFailureDao;
import net.paguo.dao.ClientComplaintDao;

import java.util.Date;
import java.util.List;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 25.08.2006 0:22:19
 */
public class NetworkFailureController {
    /**
     * Creates a new networkProblem
     * @param failureDescr Cause of a failure
     * @param failureTime Time of a failure discovery
     */
    public void createFailure(String failureDescr, Date failureTime){
        NetworkProblem f = new NetworkProblem();
        f.setFailureDescription(failureDescr);
        f.setFailureTime(failureTime);
        getProblemDao().create(f);
    }

    /**
     * Creates a client complaint
     * @param failureDescr Cause of a complaint
     * @param failureTime  Time of a complaint receive
     * @param ci client
     */
    public void createComplaint(String failureDescr, Date failureTime, ClientItem ci){
        ClientComplaint f = new ClientComplaint();
        f.setFailureDescription(failureDescr);
        f.setFailureTime(failureTime);
        f.setClient(ci);
        getComplaintDao().create(f);
    }

    public void assignResolution(NetworkFailure failure, FailureRestore issue){
        failure.setRestoreAction(issue);
        getFailureDao().update(failure);
    }

    public void assignResolution(NetworkProblem problem, FailureRestore issue){
        problem.setRestoreAction(issue);
        if (issue.getCompleted()){
            List<ClientComplaint> complaints = problem.getDependedComplaints();
            for(ClientComplaint complaint : complaints){
               FailureRestore p = new FailureRestore();
                p.setRestoreTime(issue.getRestoreTime());
                p.setRestoreAction("CLOSED BY PARENT");
                p.setCompleted(true);
                complaint.setRestoreAction(p);
                getComplaintDao().update(complaint);
            }
        }
    }

    public List<NetworkFailure> findAllFailures(){
        return getFailureDao().readAll();
    }

    public List<NetworkProblem> findAllProblems(){
        return getProblemDao().readAll();
    }

    public ClientComplaintDao getComplaintDao() {
        return complaintDao;
    }

    public void setComplaintDao(ClientComplaintDao ccd){
        this.complaintDao = ccd;
    }

    public List<ClientComplaint> findByClient(ClientItem ci){
        return getComplaintDao().findByClient(ci);
    }

    public List<ClientComplaint> findAllComplaints(){
        return getComplaintDao().readAll();
    }


    public NetworkFailureDao getFailureDao() {
        return failureDao;
    }

    public void setFailureDao(NetworkFailureDao failureDao) {
        this.failureDao = failureDao;
    }


    public net.paguo.dao.NetworkProblemDao getProblemDao() {
        return problemDao;
    }

    public void setProblemDao(net.paguo.dao.NetworkProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    private NetworkFailureDao failureDao;
    private ClientComplaintDao complaintDao;
    private net.paguo.dao.NetworkProblemDao problemDao;

    public List<NetworkFailure> findOpen() {
        return getFailureDao().findOpen();
    }

    public List<NetworkProblem> findOpenProblems(){
       return getProblemDao().findOpen(); 
    }
}
