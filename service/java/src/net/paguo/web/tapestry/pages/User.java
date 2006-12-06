package net.paguo.web.tapestry.pages;

import com.javaforge.tapestry.spring.annotations.InjectSpring;
import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.users.LocalUser;
import org.apache.tapestry.IPage;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.valid.ValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: slava
 * Date: 08.11.2006
 * Time: 0:05:59
 * Version: $Id$
 */
public abstract class User extends BasePage implements PageBeginRenderListener {
    public static final Log log = LogFactory.getLog(User.class);

    public abstract LocalUser getSelectedUser();

    public abstract void setSelectedUser(LocalUser user);

    @Persist("session")
    public abstract Integer getCurrentUserId();

    public abstract void setCurrentUserId(Integer userId);


    @Persist("session")
    public abstract LocalUser getOldUser();

    public abstract void setOldUser(LocalUser oldUser);

    @InjectSpring(value = "usersController")
    public abstract UsersController getController();

    @Bean
    public abstract ValidationDelegate getDelegate();

    public void pageBeginRender(PageEvent event) {
        LocalUser user;
        boolean newUser = getCurrentUserId() == null;
        if (newUser) {
            user = new LocalUser();
            setOldUser(user);
        } else {
            user = getController().readUser(getCurrentUserId());
            if (!event.getRequestCycle().isRewinding()) {
               setOldUser(getController().readUser(getCurrentUserId()));
            } else {
                if (!getOldUser().equals(user)) {
                    throw new PageRedirectException("Home");
                }
            }
        }
        setSelectedUser(user);
        log.info("Current object is " + user);
    }

    public IPage onSave() {
        ValidationDelegate delegate = getDelegate();
        if (delegate.getHasErrors()) {
            return null;
        }
        LocalUser user = getSelectedUser();
        try {
            log.info("Saving object " + user);
            getController().saveUser(user);
        } catch (ControllerException e) {
            delegate.record(new ValidatorException(e.getMessage()));
            throw new PageRedirectException(getPageName());
        }
        return getHomePage();
    }

    @InjectPage("Home")
    public abstract IPage getHomePage();
}
