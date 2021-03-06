package net.paguo.controller;

import net.paguo.controller.exception.ControllerException;
import net.paguo.dao.ClientComplaintDao;
import net.paguo.dao.NetworkFailureDao;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.FailureRestore;
import net.paguo.domain.problems.NetworkFailure;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.commons.lang.math.IntRange;
import org.apache.wicket.util.string.IStringSequence;

import java.util.Date;
import java.util.List;
import java.util.Collection;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 25.08.2006 0:22:19
 */
public class NetworkFailureController {

    /**
     * Creates a new networkProblem
     *
     * @param failureDescr Cause of a failure
     * @param failureTime  Time of a failure discovery
     */
    public void createFailure(String failureDescr, Date failureTime) {
        NetworkProblem f = new NetworkProblem();
        f.setFailureDescription(failureDescr);
        f.setFailureTime(failureTime);
        getProblemDao().create(f);
    }

    /**
     * Creates a client complaint
     *
     * @param failureDescr Cause of a complaint
     * @param failureTime  Time of a complaint receive
     * @param ci           client
     */
    public void createComplaint(String failureDescr, Date failureTime, ClientItem ci) {
        ClientComplaint f = new ClientComplaint();
        f.setFailureDescription(failureDescr);
        f.setFailureTime(failureTime);
        f.setClient(ci);
        getComplaintDao().create(f);
    }

    public void assignResolution(NetworkFailure failure, FailureRestore issue) {
        failure.setRestoreAction(issue);
        getFailureDao().update(failure);
    }

    public void assignResolution(NetworkProblem problem, FailureRestore issue) {
        problem.setRestoreAction(issue);
        closeDependedComplaints(issue, problem);
    }

    public void closeDependedComplaints(FailureRestore issue, NetworkFailure failure) {
        NetworkProblem problem = getProblemDao().read(failure.getId());
        if (problem != null && issue.getCompleted()) {
            List<ClientComplaint> complaints = problem.getDependedComplaints();
            for (ClientComplaint complaint : complaints) {
                FailureRestore p = new FailureRestore();
                p.setRestoreTime(issue.getRestoreTime());
                p.setRestoreAction("CLOSED BY PARENT");
                p.setCompleted(true);
                complaint.setRestoreAction(p);
                getComplaintDao().update(complaint);
            }
        }
    }

    public List<NetworkFailure> findAllFailures() {
        return getFailureDao().readAll();
    }

    public List<NetworkProblem> findAllProblems() {
        return getProblemDao().readAll();
    }

    public List<NetworkProblem> getProblems(int from, int count){
        return getProblemDao().readPart(count, from);
    }

    public List<NetworkProblem> getProblems(int from, int count, boolean asc){
        return getProblemDao().readPart(count, from, "failureTime", asc);
    }

    public List<NetworkProblem> getProblems(IntRange count){
        return getProblemDao().findAll(count);
    }

    public ClientComplaint getClientComplaint(Integer id) {
        return getComplaintDao().read(id);
    }

    public List<ClientComplaint> getClientComplaints(int from, int count){
        return getComplaintDao().readPart(count, from);
    }

    public NetworkProblem getNetworkProblem(Integer id) {
        return getProblemDao().read(id);
    }

    public ClientComplaintDao getComplaintDao() {
        return complaintDao;
    }

    public void setComplaintDao(ClientComplaintDao ccd) {
        this.complaintDao = ccd;
    }

    public List<ClientComplaint> findByClient(ClientItem ci) {
        return getComplaintDao().findByClient(ci);
    }

    public List<ClientComplaint> findAllComplaints() {
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

    public List<NetworkProblem> findOpenProblems() {
        return getProblemDao().findOpen();
    }

    public void saveCrash(NetworkProblem crash) throws ControllerException {
        try {
            if (crash.getId() == null) {
                getProblemDao().create(crash);
            } else {
                getProblemDao().update(crash);
            }
        } catch (Throwable t) {
            throw new ControllerException(t);
        }
    }

    public int getProblemsCount() {
        return getProblemDao().maxCount();
    }

    public void deleteProblem(NetworkProblem problem) throws ControllerException {
        try{
            getProblemDao().delete(problem);
        }catch (Throwable t){
            throw new ControllerException(t);
        }
    }

    public List<ClientComplaint> getClientComplaints(IntRange range) {
       return getComplaintDao().findAll(range);
    }

    public int getComplaintsCount(){
        return getComplaintDao().maxCount();
    }

    public void deleteComplaint(ClientComplaint problem) throws ControllerException{
        try{
            getComplaintDao().delete(problem);
        } catch (Throwable t){
            throw new ControllerException(t);
        }
    }

    public void saveComplaint(ClientComplaint problem) throws ControllerException {
        try {
            if (problem.getId() == null){
                getComplaintDao().create(problem);
            } else {
                getComplaintDao().update(problem);
            }
        } catch (Throwable t) {
            throw new ControllerException(t);
        }
    }

    public Collection<ClientComplaint> getComplaints(Date start, Date end) {
        return getComplaintDao().findByDateRange(start, end);
    }

    public Collection<NetworkProblem> getProblems(Date start, Date end) {
        return getProblemDao().findByDateRange(start, end);
    }
}
