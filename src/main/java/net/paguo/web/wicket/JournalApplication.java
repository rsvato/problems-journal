package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * User: sreentenko
 * Date: 30.05.2007
 * Time: 0:51:30
 */
public class JournalApplication extends WebApplication implements IRoleCheckingStrategy {

    @SpringBean
    private UsersController usersController;

    @Override
    public Class getHomePage() {
        return ComplaintsPage.class;
    }

    @Override
    public IExceptionSettings getExceptionSettings() {
        return super.getExceptionSettings();
    }

    @Override
    protected void init() {
        addComponentInstantiationListener(new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(new RoleAuthorizationStrategy(this));
        mountBookmarkablePage("/login", Login.class);
        mountBookmarkablePage("/dashboard", Dashboard.class);
        mountBookmarkablePage("/problems", NetworkProblemsPage.class);
        mountBookmarkablePage("/complaints", ComplaintsPage.class);
        mountBookmarkablePage("/add-crash", NetworkProblemCreatePage.class);
        mountBookmarkablePage("/add-complaint", ComplaintCreatePage.class);
        mountBookmarkablePage("/admin/users", Users.class);
        mountBookmarkablePage("/admin/groups", GroupPage.class);
        mountBookmarkablePage("/admin/roles", RolesManagementPage.class);
        mountBookmarkablePage("/admin/appRoles", ApplicationRolesPage.class);
        mountBookmarkablePage("/test.html", TablePOC.class);
        mountBookmarkablePage("/create-request", ChangeStatusRequestCreatePage.class);
        mountBookmarkablePage("/requests", ChangeStatusRequestListPage.class);

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

    public boolean hasAnyRole(Roles roles) {
        final Roles savedRoles = JournalWebSession.get().getRoles();
        return savedRoles != null && savedRoles.hasAnyRole(roles);
    }
}
