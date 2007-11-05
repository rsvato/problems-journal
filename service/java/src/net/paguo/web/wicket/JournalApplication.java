package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import wicket.ISessionFactory;
import wicket.Session;
import wicket.protocol.http.WebApplication;
import wicket.spring.injection.annot.SpringBean;
import wicket.spring.injection.annot.SpringComponentInjector;

/**
 * User: sreentenko
 * Date: 30.05.2007
 * Time: 0:51:30
 */
public class JournalApplication extends WebApplication {

    @SpringBean
    private UsersController usersController;

    @Override
    public Class getHomePage() {
        return Dashboard.class;
    }

    @Override
    protected void init() {
        addComponentInstantiationListener(new SpringComponentInjector(this));
        mountBookmarkablePage("/login", Login.class);
        mountBookmarkablePage("/dashboard", Dashboard.class);
        mountBookmarkablePage("/users", Users.class);
    }

    @Override
    protected ISessionFactory getSessionFactory() {
        return new ISessionFactory() {
            public Session newSession() {
                return new JournalWebSession(JournalApplication.this);
            }
        };
    }

    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }
}
