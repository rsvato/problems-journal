package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

import net.paguo.controller.ClientItemController;
import net.paguo.web.tapestry.pages.models.ClientSelectionModel;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 06.10.2006
 * Time: 0:49:07
 * Version: $Id$
 */
public abstract class Home extends BasePage {
    private static final Log log = LogFactory.getLog(Home.class);

    @InjectSpring(value="clientController")
    public abstract ClientItemController getClientController();

    @InjectPage("Result")
    public abstract IPage getResultPage();

    @InjectPage("Users")
    public abstract IPage getUsersPage();

    @InjectPage("Clients")
    public abstract IPage getClientsPage();

    @InitialValue("literal:MSFT")
    public abstract String getStockId();

    public abstract Date getInputDate();

    public abstract Integer getClientId();

    public IPage onOk(IRequestCycle cycle) {
        Clients page = (Clients) getClientsPage();
        page.setClientId(getClientId());
        return page;
    }


    public String getSubjectName() {
        return "Taburetka";
    }

    public IPropertySelectionModel getClientList(){
        ClientItemController controller = getClientController();
        return new ClientSelectionModel(controller.getAllClients());
    }

}
