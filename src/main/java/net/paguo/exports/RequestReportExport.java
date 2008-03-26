package net.paguo.exports;

import net.paguo.controller.ChangeStatusRequestController;
import net.paguo.domain.requests.ChangeStatusRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;

import java.util.Collection;
import java.util.Date;

/**
 * @author Reyentenko
 */
public class RequestReportExport {
    private static final Log log = LogFactory.getLog(RequestReportExport.class);
    private ChangeStatusRequestController controller;

    public ChangeStatusRequestController getController() {
        return controller;
    }

    public void setController(ChangeStatusRequestController controller) {
        this.controller = controller;
    }

    public final HSSFWorkbook exportReport(Date start, Date end) {
        final Collection<ChangeStatusRequest> requests = getController().getByDates(start, end);
        return createWorkbook(requests);
    }

    protected HSSFWorkbook createWorkbook(Collection<ChangeStatusRequest> requests){
        HSSFWorkbook wb = new HSSFWorkbook();

        final HSSFSheet hssfSheet = wb.createSheet("Main");
        int i = 1;
        for (ChangeStatusRequest request : requests) {
            final HSSFRow hssfRow = hssfSheet.createRow(i++);
            final HSSFCell cell = hssfRow.createCell((short) 0);
            cell.setCellValue(new HSSFRichTextString(request.getEnteredClient()));
        }

        return wb;
    }
}
