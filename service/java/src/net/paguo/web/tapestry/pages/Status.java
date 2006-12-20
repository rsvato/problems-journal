package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import net.paguo.controller.ChangeStatusRequestController;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

import java.util.List;

/**
 * User: slava
 * Date: 17.12.2006
 * Time: 2:42:05
 * Version: $Id$
 */
public abstract class Status extends BasePage {

    @InjectSpring(value = "changeStatusRequestController")
    public abstract ChangeStatusRequestController getController();

    public List getRequestList(){
        return (List) getController().getAll();
    }
}
