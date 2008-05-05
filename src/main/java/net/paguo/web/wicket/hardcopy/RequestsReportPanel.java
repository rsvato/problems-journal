package net.paguo.web.wicket.hardcopy;

import net.paguo.exports.AbstractExcelExporter;
import net.paguo.web.wicket.resources.ExcelResourceStreamWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;

import java.io.Serializable;
import java.util.Date;

/**
 * User: sreentenko
 * Date: 26.03.2008
 * Time: 0:39:24
 */
public class RequestsReportPanel extends Panel {
    private AbstractExcelExporter exporter;
    private static final long serialVersionUID = -4707990886745138286L;

    public AbstractExcelExporter getExporter() {
        return exporter;
    }

    public void setExporter(AbstractExcelExporter excelExporter) {
        this.exporter = excelExporter;
    }

    public RequestsReportPanel(String id, AbstractExcelExporter excelExporter) {
        super(id);
        this.exporter = excelExporter;
        final RequestReportForm child = new RequestReportForm("form");
        add(child);
    }

    private class RequestReportForm extends Form {
        private static final long serialVersionUID = 3354398375425781943L;

        public RequestReportForm(String s) {
            super(s, new CompoundPropertyModel(new RequestReportFormParameters()));
            add(new DateField("startDate"));
            add(new DateField("endDate"));
        }

        protected void onSubmit() {
            final RequestReportFormParameters parameters
                    = (RequestReportFormParameters) getModelObject();
            final HSSFWorkbook workbook = getExporter().
                    exportReport(parameters.getStartDate(), parameters.getEndDate());
            ExcelResourceStreamWriter writer = new ExcelResourceStreamWriter(null, workbook);
            IRequestTarget rt = new ResourceStreamRequestTarget(writer, "export.xls");
            RequestCycle.get().setRequestTarget(rt);
        }
    }

    public class RequestReportFormParameters implements Serializable {
        private Date startDate;
        private Date endDate;
        private static final long serialVersionUID = 1523288201123248218L;

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
