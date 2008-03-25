package net.paguo.web.wicket.resources;


import net.paguo.dao.ChangeStatusRequestDao;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.web.wicket.hardcopy.RequestsReportDownloadPage;
import org.apache.poi.hssf.usermodel.*;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Reyentenko
 */
public class ExcelResourceImpl extends BinaryResource {
    private static final long serialVersionUID = -3106910267810512508L;
    private RequestsReportDownloadPage.RequestReportFormParameters params;

    @SpringBean(name = "changeStatusRequestDao")
    private ChangeStatusRequestDao requestDao;

    public ChangeStatusRequestDao getRequestDao() {
        return requestDao;
    }

    public void setRequestDao(ChangeStatusRequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public ExcelResourceImpl(String name) {
        super(name, APPLICATION_VND_MS_EXCEL);
    }

    protected InputStream getResource() throws IOException {
        ByteArrayOutputStream baos = createWorkbook();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    protected ByteArrayOutputStream createWorkbook() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HSSFWorkbook wb = new HSSFWorkbook();

        final List<ChangeStatusRequest> list
                = getRequestDao().findByDates(params.getStartDate(), params.getEndDate());


        final HSSFSheet hssfSheet = wb.createSheet("Main");
        int i = 1;
        for (ChangeStatusRequest request : list) {
            final HSSFRow hssfRow = hssfSheet.createRow(i++);
            final HSSFCell cell = hssfRow.createCell((short) 0);
            cell.setCellValue(new HSSFRichTextString(request.getEnteredClient()));
        }

        wb.write(baos);
        baos.flush();
        return baos;
    }


    public void setParams(RequestsReportDownloadPage.RequestReportFormParameters parameters) {
        this.params = parameters;
    }
}
