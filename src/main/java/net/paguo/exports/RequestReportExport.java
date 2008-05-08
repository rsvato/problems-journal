package net.paguo.exports;

import net.paguo.controller.ChangeStatusRequestController;
import net.paguo.domain.requests.ChangeStatusRequest;

import java.util.Collection;
import java.util.Date;

/**
 * @author Reyentenko
 */
public class RequestReportExport extends AbstractExcelExporter<ChangeStatusRequest> {
    private ChangeStatusRequestController controller;

    public ChangeStatusRequestController getController() {
        return controller;
    }

    public void setController(ChangeStatusRequestController controller) {
        this.controller = controller;
    }

    public Collection<ChangeStatusRequest> getData(Date start, Date end) {
        final Collection<ChangeStatusRequest> requests = getController().getByDates(start, end);
        return requests;
    }

}
