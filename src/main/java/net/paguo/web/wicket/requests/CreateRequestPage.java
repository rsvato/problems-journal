package net.paguo.web.wicket.requests;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.*;
import net.paguo.web.wicket.SecuredWebPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValueConversionException;

import java.util.Date;

/**
 * @author Reyentenko
 */
public class CreateRequestPage extends SecuredWebPage {

    private static final long serialVersionUID = 4589292378207230195L;
    private static final Log log = LogFactory.getLog(CreateRequestPage.class);

    @SpringBean
    private RequestController controller;


    public RequestController getController() {
        return controller;
    }

    public void setController(RequestController controller) {
        this.controller = controller;
    }

    public CreateRequestPage(PageParameters parameters) {
        Request req = newRequest();
        try {
            Integer requestId = parameters.getInt("request");
            req = getController().get(requestId);
        } catch (StringValueConversionException e) {
            log.debug("Missing or invalid parameter request");
        }

        final Request request = req;
        Form reqForm = new Form("form", new CompoundPropertyModel(request)){
            private static final long serialVersionUID = 7215839440637336957L;

            @Override
            protected void onSubmit() {
                Request rqe = request;
                rqe.setCreationDate(new Date());
                rqe.setAuthor(findSessionUser());
                if (rqe.getId() == null){
                    rqe.setCurrentStage(ProcessStage.OPENED);
                }else{
                    if (rqe.getService() != null){
                        rqe.setCurrentStage(ProcessStage.BEFORE_TESTING);
                    }
                }
                getController().saveRequest(rqe);
            }

            @Override
            protected void validate() {
                super.validate();
            }
        };

        

        reqForm.add(new SimpleClassPanel("clientInfo", request.getClientInformation()));
        reqForm.add(new SimpleClassPanel("contactInfo", request.getClientInformation().getContact()));
        reqForm.add(new SimpleClassPanel("buildingInfo", request.getBuildingInformation()));
        reqForm.add(new SimpleClassPanel("addressInformation", request.getClientInformation().getAddress()));
        WebMarkupContainer container = new WebMarkupContainer("serviceHolder");
        final boolean visible = ProcessStage.OPENED == req.getCurrentStage();
        if (visible){
            container.add(new SimpleClassPanel("service", request.getService()));
        }
        container.setVisible(visible);
        reqForm.add(container);
        add(reqForm);
        add(HeaderContributor.forCss(CreateRequestPage.class, "forms.css"));
        add(new FeedbackPanel("panel"));
    }

    private Request newRequest() {
        Request req = new Request();
        final ClientInformation clientInformation = new ClientInformation();
        clientInformation.setContact(new ClientContactInformation());
        clientInformation.setAddress(new AddressInformation());
        req.setClientInformation(clientInformation);
        req.setBuildingInformation(new BuildingInformation());
        return req;
    }
}
