package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.IPage;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.valid.ValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;
import net.paguo.domain.clients.ClientItem;
import net.paguo.controller.ClientItemController;
import net.paguo.controller.exception.ControllerException;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 07.12.2006
 * Time: 0:38:39
 * Version: $Id$
 */
public abstract class BFClients extends BasePage implements PageBeginRenderListener {
    @Persist("client")
    public abstract Integer getSelectedClientId();
    public abstract void setSelectedClientId(Integer id);

    @Persist("client")
    public abstract ClientItem getOldClient();
    public abstract void setOldClient(ClientItem client);

    @Persist("session")
    public abstract ClientItem getClient();
    public abstract void setClient(ClientItem client);

    @InjectSpring(value="clientController")
    public abstract ClientItemController getClientController();

    @InjectPage("Home")
    public abstract IPage getHomePage();

    @Bean
    public abstract ValidationDelegate getDelegate();

    public void pageBeginRender(PageEvent event) {
        System.err.println("pageBeginRender:<<<");
        ClientItem item;
        if (getSelectedClientId() == null){
            item = new ClientItem();
        }else{
            item = getClientController().readClient(getSelectedClientId());
            if (! getRequestCycle().isRewinding()){
                setOldClient(getClientController().readClient(getSelectedClientId()));
            }else{
                if (! getOldClient().equals(item)){
                    throw new PageRedirectException("Home");
                }
            }
        }
        setClient(item);
        System.err.println(getClient());
    }

    public IPage save(){
        ValidationDelegate delegate = getDelegate();
        if (delegate.getHasErrors()){
            return null;
        }
        ClientItem item = getClient();
        item.setDeleted(Boolean.FALSE);
        try {
            getClientController().save(item);
        } catch (ControllerException e) {
            delegate.record(new ValidatorException(e.getMessage()));
            throw new PageRedirectException(getPageName());
        }
        return getHomePage();
    }
}
