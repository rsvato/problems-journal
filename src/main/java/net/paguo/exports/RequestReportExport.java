package net.paguo.exports;

import net.paguo.controller.ChangeStatusRequestController;
import net.paguo.domain.requests.ChangeStatusRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.context.MessageSource;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Reyentenko
 */
public class RequestReportExport {
    private static final Log log = LogFactory.getLog(RequestReportExport.class);
    private ChangeStatusRequestController controller;
    private MessageSource messageSource;
    private List<String> headers;
    private List<String> properties;

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

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
        final HSSFCellStyle style = createHeaderStyle(wb);
        final HSSFCellStyle dateStyle = createDateStyle(wb);
        final HSSFSheet hssfSheet = wb.createSheet("Main");

        addHeader(hssfSheet, style);
        int i = 1;
        for (ChangeStatusRequest request : requests) {
            final HSSFRow row = hssfSheet.createRow(i++);
            short j = 0;
            for (String property : getProperties()) {
                HSSFCell cell = row.createCell(j++);

                setCellProperty(cell, getObjectValue(request, property), dateStyle);

                if (request.isPermanent()){
                    HSSFCellStyle hssfCellStyle = cell.getCellStyle();
                    if (hssfCellStyle == null){
                       hssfCellStyle = wb.createCellStyle();
                    }
                    hssfCellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
                    cell.setCellStyle(hssfCellStyle);
                }
            }
        }
        log.debug("Workbook successfully created");
        return wb;
    }

    private HSSFCellStyle createHeaderStyle(HSSFWorkbook wb) {
        final HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName(HSSFFont.FONT_ARIAL);
        final HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        return style;
    }

    private HSSFCellStyle createDateStyle(HSSFWorkbook wb) {
        final HSSFCellStyle style = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();
        style.setDataFormat(format.getFormat("d/m/yy hh:mm:ss"));
        return style;
    }

    private Object getObjectValue(Object o, String path) {
        Object property = null;
        try {
            property = PropertyUtils.getProperty(o, path);
        } catch (Throwable t) {
            property = "";
        }
        return property;
    }

    private void setCellProperty(HSSFCell cell, Object value, HSSFCellStyle dateStyle) {
        if (value == null) {
            cell.setCellValue(new HSSFRichTextString(""));
        } else {
            if (value instanceof Date) {
                cell.setCellValue((Date) value);
                cell.setCellStyle(dateStyle);
            } else {
                cell.setCellValue(
                        new HSSFRichTextString(String.valueOf(value)));
            }
        }
    }

    private void addHeader(HSSFSheet sheet, HSSFCellStyle style) {
        final HSSFRow row = sheet.createRow(0);
        int i = 0;
        for (String header : getHeaders()) {
            final HSSFCell cell = row.createCell((short) i++);
            cell.setCellStyle(style);
            cell.setCellValue(new HSSFRichTextString(getMessage(header)));
        }
    }

    private String getMessage(final String code) {
        return getMessageSource().getMessage(code, null, new Locale("ru", "RU"));
    }
}
