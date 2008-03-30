package net.paguo.exports;

import net.paguo.controller.ChangeStatusRequestController;
import net.paguo.domain.requests.ChangeStatusRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.context.MessageSource;

import java.util.Collection;
import java.util.Date;

/**
 * @author Reyentenko
 */
public class RequestReportExport {
    private static final Log log = LogFactory.getLog(RequestReportExport.class);
    private ChangeStatusRequestController controller;
    private MessageSource messageSource;

    public ChangeStatusRequestController getController() {
        return controller;
    }

    public void setController(ChangeStatusRequestController controller) {
        this.controller = controller;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public final HSSFWorkbook exportReport(Date start, Date end) {
        final Collection<ChangeStatusRequest> requests = getController().getByDates(start, end);
        log.debug("Collection size: " + requests.size());
        HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName(HSSFFont.FONT_ARIAL);
        final HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

        final HSSFSheet hssfSheet = wb.createSheet("Main");
        addHeader(hssfSheet, style);
        int i = 1;
        for (ChangeStatusRequest request : requests) {
            final HSSFRow hssfRow = hssfSheet.createRow(i++);
            final HSSFCell cell = hssfRow.createCell((short) 0);
            cell.setCellValue(new HSSFRichTextString(request.getEnteredClient()));
        }
        log.debug("Workbook successfully created");
        return wb;
    }

    private void addHeader(HSSFSheet sheet, HSSFCellStyle style) {
        final HSSFRow row = sheet.createRow(0);
        final HSSFCell cell = row.createCell((short) 0);
        cell.setCellStyle(style);
        cell.setCellValue(new HSSFRichTextString(getMessage("excel.client.header")));
    }

    private String getMessage(final String code) {
        return getMessageSource().getMessage(code, null, null);
    }
}
