package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.JournalAuthenticationException;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.users.ApplicationRole;
import static net.paguo.domain.users.ApplicationRole.Action.VIEW;
import net.paguo.domain.users.LocalUser;
import net.paguo.web.wicket.auth.UserView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: sreentenko
 * Date: 04.06.2007
 * Time: 0:52:35
 */
public class SecuredWebPage extends ApplicationWebPage {
    private static final Log log = LogFactory.getLog(SecuredWebPage.class);

    @SpringBean
    private UsersController usersController;

    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }

    protected LocalUser findSessionUser() {
        JournalWebSession session = (JournalWebSession) Session.get();
        final UserView authenticatedUser = session.getAuthenticatedUser();
        if (authenticatedUser != null) {
            final String username = authenticatedUser.getUsername();
            try {
                return getUsersController().findByPermission(username);
            } catch (JournalAuthenticationException e) {
                log.error("Cannot found permissions for user " + username);
                throw new RestartResponseAtInterceptPageException(Login.class);
            }
        } else {
            log.error("No authenticated user found in the middle of...");
            throw new RestartResponseAtInterceptPageException(Login.class);
        }
    }

    public SecuredWebPage() {
        super();
        verifyAccess();
        addLinks();

    }

    @Override
    protected void addLinks() {

        boolean isAdminRole = checkRole("ROLE_SUPERVISOR");


        final BookmarkablePageLink complaintLink = new BookmarkablePageLink("complaints", ComplaintsPage.class);
        secureElement(complaintLink, ClientComplaint.class,
                VIEW);
        add(complaintLink);
        final BookmarkablePageLink problemLink = new BookmarkablePageLink("problems", NetworkProblemsPage.class);
        secureElement(problemLink, NetworkProblem.class, VIEW);
        add(problemLink);

        final BookmarkablePageLink requestLink = new BookmarkablePageLink("requests", ChangeStatusRequestListPage.class);
        secureElement(requestLink, ChangeStatusRequest.class, VIEW);
        add(requestLink);

        add(new BookmarkablePageLink("users", Users.class).setVisible(isAdminRole));
        add(new BookmarkablePageLink("groups", GroupPage.class).setVisible(isAdminRole));
        add(new BookmarkablePageLink("roles", RolesManagementPage.class).setVisible(isAdminRole));
    }

    protected void verifyAccess() {
        if (!isUserAuthenticated()) {
            log.debug("Authentication information not found. Redirecting to Login page");
            throw new RestartResponseAtInterceptPageException(Login.class);
        }
        AllowedRole declaredRole = getClass().getAnnotation(AllowedRole.class);
        if (declaredRole != null) {
            if (!checkRole(declaredRole.value())) {
                log.debug("Required authority not found: "
                        + declaredRole.value() + ". Redirecting to Login page");
                Session.get().invalidate();
                throw new RestartResponseAtInterceptPageException(Login.class);
            }
        }
    }

    protected boolean checkRole(String s) {
        JournalWebSession session = (JournalWebSession) getSession();
        return session.isHasAuthority(s);
    }

    protected boolean isUserAuthenticated() {
        JournalWebSession session = (JournalWebSession) getSession();
        return session.isAuthenticated();
    }

    protected String findRoleForAction(Class klass, ApplicationRole.Action action) {
        final ApplicationRole role = getUsersController().findARForClassAndAction(klass, action);
        return role != null ? role.getRole() : "NOBODY"; //disallow when no role created for action
    }

    protected void secureElement(Component cmp, Class klass, List<ApplicationRole.Action> actions) {
        Set<String> roles = new HashSet<String>();
        if (actions != null) {
            for (ApplicationRole.Action action : actions) {
                roles.add(findRoleForAction(klass, action));
            }
        }
        String allowed = StringUtils.join(roles.iterator(), ",");
        MetaDataRoleAuthorizationStrategy.authorize(cmp, RENDER, allowed);
    }

    protected void secureElement(Component cmp, Class klass, ApplicationRole.Action... actions) {
        Set<String> roles = new HashSet<String>();
        if (actions != null) {
            for (ApplicationRole.Action action : actions) {
                roles.add(findRoleForAction(klass, action));
            }
        }
        String allowed = StringUtils.join(roles.iterator(), ",");
        MetaDataRoleAuthorizationStrategy.authorize(cmp, RENDER, allowed);
    }
}
