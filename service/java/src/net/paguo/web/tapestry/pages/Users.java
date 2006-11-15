package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.annotations.Persist;
import net.paguo.domain.users.LocalUser;
import net.paguo.controller.ClientItemController;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 08.11.2006
 * Time: 0:05:59
 * Version: $Id$
 */
public abstract class Users extends BasePage {

    public abstract LocalUser getUser();
    public abstract void setUser(LocalUser user);

    @Persist("user")
    public abstract Integer getCurrentUserId();
    public abstract void setCurrentUserId(Integer userId);


    @Persist("user")
    public abstract LocalUser getOldUser();
    public abstract void setOldUser(LocalUser oldUser);

    @InjectSpring(value="clientController")
    public abstract ClientItemController getClientController();
}
