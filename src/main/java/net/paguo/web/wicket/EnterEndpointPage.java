package net.paguo.web.wicket;

import net.paguo.controller.ChangeStatusRequestController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.equipment.ClientEndpoint;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.RequestInformation;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;

/**
 * @author Reyentenko
 */
public class EnterEndpointPage extends SecuredWebPage {
    @SpringBean
    private ChangeStatusRequestController controller;
    private static final long serialVersionUID = 7607608350253465300L;
    private ChangeStatusRequest changeRequest;


    public ChangeStatusRequestController getController() {
        return controller;
    }

    public void setController(ChangeStatusRequestController controller) {
        this.controller = controller;
    }

    public EnterEndpointPage(IModel model) {
        final ChangeStatusRequest o = (ChangeStatusRequest) model.getObject();
        changeRequest = o;
        ClientEndpoint endpoint = o.getEndpoint();
        if (endpoint == null) {
            endpoint = new ClientEndpoint();
        }
        final CompoundPropertyModel cmModel = new CompoundPropertyModel(endpoint);
        final EnterEndpointForm form = new EnterEndpointForm("form", cmModel);
        add(form);
        form.add(new EndpointPanel("panel", cmModel));
        add(new FeedbackPanel("feedback"));
        add(HeaderContributor.forCss(EnterEndpointPage.class, "wstyles.css"));
    }


    public void saveEndpointAndProgress(ClientEndpoint ce) {
        RequestInformation ri = new RequestInformation();
        ri.setAuthor(findSessionUser());
        ri.setDateEntered(new Date());
        try {
            getController().saveEndpointToRequest(ce, changeRequest, ri);
            setResponsePage(ChangeStatusRequestListPage.class);
        } catch (ControllerException e) {
            Session.get().error(e.getMessage());
        }

    }

    private class EnterEndpointForm extends Form {
        private static final long serialVersionUID = 7798142387757771005L;

        public EnterEndpointForm(String id, IModel model) {
            super(id, model);
        }

        protected void onSubmit() {
            final ClientEndpoint ce = (ClientEndpoint) getModelObject();
            saveEndpointAndProgress(ce);
        }
    }

}
