package net.paguo.exports;

import net.paguo.domain.requests.ChangeStatusRequest;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.context.MessageSource;

import java.util.*;

/**
 * @author Reyentenko
 */
public abstract class AbstractExcelExporter {
    private static final Log log = LogFactory.getLog(AbstractExcelExporter.class);
    private MessageSource messageSource;
    private List<String> headers;
    private List<String> properties;
    private Map<String, String> formatters;

    public Map<String, String> getFormatters() {
        return formatters;
    }

    public void setFormatters(Map<String, String> formatters) {
        this.formatters = formatters;
    }

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

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public final HSSFWorkbook exportReport(Date start, Date end) {
        final Collection requests = getData(start, end);
        log.debug("Collection size: " + requests.size());
        HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFCellStyle style = createHeaderStyle(wb);
        final HSSFCellStyle dateStyle = createDateStyle(wb);
        final HSSFSheet hssfSheet = wb.createSheet("Main");

        addHeader(hssfSheet, style);
        int i = 1;
        for (Object request : requests) {
            final HSSFRow row = hssfSheet.createRow(i++);
            short j = 0;
            for (String property : getProperties()) {
                HSSFCell cell = row.createCell(j++);
                setCellProperty(cell, getObjectValue(request, property), dateStyle);
            }
        }
        log.debug("Workbook successfully created");
        return wb;
    }

    public abstract Collection<ChangeStatusRequest> getData(Date start, Date end);

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
        final String formatterId = getFormatters().get(path);
        if (StringUtils.isNotEmpty(formatterId)){
            property = getMessage(formatterId, property);
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

    private String getMessage(final String code, final Object... args) {
        final String message = getMessageSource().getMessage(code, args, new Locale("ru", "RU"));
        return message;
    }
}
