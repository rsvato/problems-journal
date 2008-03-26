package net.paguo.web.wicket.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Reyentenko
 */
public class ExcelResourceStreamWriter extends AbstractResourceStreamWriter {

    protected static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";
    private String filename;
    private HSSFWorkbook workbook;
    private static final Log log = LogFactory.getLog(ExcelResourceStreamWriter.class);
    private static final long serialVersionUID = -6915066491658330092L;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public ExcelResourceStreamWriter(String filename, HSSFWorkbook wb){
        this.filename = filename;
        this.workbook = wb;
    }

    public final void write(OutputStream output) {
        try {
            getWorkbook().write(output);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public String getContentType() {
        return APPLICATION_VND_MS_EXCEL;
    }


}
