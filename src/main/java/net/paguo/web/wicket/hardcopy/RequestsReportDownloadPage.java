package net.paguo.web.wicket.hardcopy;

import net.paguo.web.wicket.SecuredWebPage;
import net.paguo.web.wicket.resources.ExcelResourceImpl;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.CompoundPropertyModel;

import java.io.Serializable;
import java.util.Date;

/**
 * User: sreentenko
 * Date: 26.03.2008
 * Time: 0:39:24
 */
public class RequestsReportDownloadPage extends SecuredWebPage {

    public RequestsReportDownloadPage() {
        final WebMarkupContainer downloadArea = new WebMarkupContainer("downloadArea");
        final ResourceLink link = new ResourceLink("download",
                new ExcelResourceImpl("report.xls"));
        downloadArea.add(link);
        downloadArea.setOutputMarkupId(true);
        downloadArea.setVisible(false);
        add(downloadArea);
        final RequestReportForm child = new RequestReportForm("form");
        child.add(new DateTimeField("startDate").setRequired(true));
        child.add(new DateTimeField("endDate").setRequired(true));
        child.add(new AjaxButton("submit") {
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                final RequestReportFormParameters parameters
                        = (RequestReportFormParameters) form.getModelObject();
                ExcelResourceImpl resource = new ExcelResourceImpl("report.xls");
                resource.setParams(parameters);
                ResourceLink newLink = new ResourceLink("download", resource);
                downloadArea.remove("download");
                downloadArea.add(newLink);
                downloadArea.setVisible(true);

            }
        });
        add(child);
    }

    private class RequestReportForm extends Form {
        public RequestReportForm(String s) {
            super(s, new CompoundPropertyModel(new RequestReportFormParameters()));
        }
    }

    public class RequestReportFormParameters implements Serializable {
        private Date startDate;
        private Date endDate;

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
