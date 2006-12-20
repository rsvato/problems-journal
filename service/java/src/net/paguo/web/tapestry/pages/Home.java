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
import java.util.Calendar;
import java.util.Locale;

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
    public static final String PAGE_NAME = "Home";

    @InjectSpring(value = "clientController")
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

    public abstract void setInputDate(Date date);

    public abstract Date getInputTime();

    public abstract Integer getSelectedClientId();

    public IPage onOk(IRequestCycle cycle) {
        log.info("Date entered is " + getInputDate());
        log.info("Time entered is " + getInputTime());
        setInputDate(addDates(getInputDate(), getInputTime()).getTime());
        log.info("Result is " + getInputDate());
        Clients page = (Clients) getClientsPage();
        page.setSelectedClientId(getSelectedClientId());
        return page;
    }

    private Calendar addDates(Date date, Date inputTime) {
        Calendar day = Calendar.getInstance();
        Calendar time = Calendar.getInstance();
        day.setTime(date);
        time.setTime(inputTime);
        day.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        day.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        return day;
    }


    public String getSubjectName() {
        return "Taburetka";
    }

    public IPropertySelectionModel getClientList() {
        ClientItemController controller = getClientController();
        return new ClientSelectionModel(controller.getAllClients());
    }

}
