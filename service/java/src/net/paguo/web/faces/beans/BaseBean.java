package net.paguo.web.faces.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.faces.application.ViewHandler;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;
import javax.faces.component.UIViewRoot;
import javax.faces.FactoryFinder;

/**
 * User: slava
 * Date: 25.12.2006
 * Time: 1:30:30
 * Version: $Id$
 */
public abstract class BaseBean {
    private Application application;

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    protected void forward(String target) {
        ViewHandler viewHandler = getApplication().getViewHandler();
        FacesContext facesCtx = getFacesContext();
        UIViewRoot view = viewHandler.createView(facesCtx, target);
        facesCtx.setViewRoot(view);
        facesCtx.renderResponse();
    }

    protected Application getApplication() {
        if (application == null) {
            ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
            application = appFactory.getApplication();
        }
        return application;
    }

    protected ServletResponse getResponse() {
        return (ServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
