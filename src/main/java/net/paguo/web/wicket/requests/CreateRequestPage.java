package net.paguo.web.wicket.requests;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.*;
import net.paguo.web.wicket.SecuredWebPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValueConversionException;

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

        Form reqForm = new Form("form");
        reqForm.add(new SimpleClassPanel("clientInfo", req.getClientInformation()));
        reqForm.add(new SimpleClassPanel("contactInfo", req.getClientInformation().getContact()));
        reqForm.add(new SimpleClassPanel("service", req.getService()));
        reqForm.add(new SimpleClassPanel("buildingInfo", req.getBuildingInformation()));
        reqForm.add(new SimpleClassPanel("addressInformation", req.getClientInformation().getAddress()));
        add(reqForm);
        add(HeaderContributor.forCss(CreateRequestPage.class, "forms.css"));
    }

    private Request newRequest() {
        Request req = new Request();
        final ClientInformation clientInformation = new ClientInformation();
        clientInformation.setContact(new ClientContactInformation());
        clientInformation.setAddress(new AddressInformation());
        req.setClientInformation(clientInformation);
        req.setBuildingInformation(new BuildingInformation());
        req.setService(new RequiredService());
        return req;
    }
}
