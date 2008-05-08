package net.paguo.exports;

import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.controller.NetworkFailureController;

import java.util.Collection;
import java.util.Date;

/**
 * @author Reyentenko
 */
public class ComplaintsExcelExporter extends AbstractExcelExporter<ClientComplaint> {
    private static final long serialVersionUID = -3980397415658789952L;

    private NetworkFailureController controller;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public Collection<ClientComplaint> getData(Date start, Date end) {
        return getController().getComplaints(start, end);
    }
}
