package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.IPage;
import org.apache.tapestry.PageRedirectException;
import net.paguo.domain.clients.ClientItem;
import net.paguo.controller.ClientItemController;
import net.paguo.controller.exception.ControllerException;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 15.11.2006
 * Time: 23:56:49
 * Version: $Id$
 */
public abstract class Clients extends BasePage implements PageBeginRenderListener {
    @Persist("client")
    public abstract Integer getClientId();
    public abstract void setClientId(Integer id);

    @Persist("client")
    public abstract ClientItem getOldClient();
    public abstract void setOldClient(ClientItem client);

    public abstract ClientItem getClient();
    public abstract void setClient(ClientItem client);

    @InjectSpring(value="clientController")
    public abstract ClientItemController getClientController();

    @InjectPage("Home")
    public abstract IPage getHomePage();

    public void pageBeginRender(PageEvent event) {
        ClientItem item;
        if (getClientId() == null){
            item = new ClientItem();
        }else{
            item = getClientController().readClient(getClientId());
            if (! getRequestCycle().isRewinding()){
                setOldClient(getClientController().readClient(getClientId()));
            }else{
                if (! getOldClient().equals(getClient())){
                    throw new PageRedirectException("Home");
                }
            }
        }
        setClient(item);
    }

    public IPage onOk(){
        ClientItem item = getClient();
        item.setDeleted(Boolean.FALSE);
        try {
            getClientController().save(item);
        } catch (ControllerException e) {
            throw new PageRedirectException(getPageName());
        }
        return getHomePage();
    }
}
