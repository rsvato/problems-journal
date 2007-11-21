package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

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
        mountBookmarkablePage("/problems", NetworkProblemsPage.class);
        mountBookmarkablePage("/add-crash", NetworkProblemCreatePage.class);
        mountBookmarkablePage("/admin/users", Users.class);
    }

    @Override
    public Session newSession(Request req, Response res) {
        return new JournalWebSession(JournalApplication.this, req);
    }

    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }
}
