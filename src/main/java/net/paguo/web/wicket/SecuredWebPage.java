package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.JournalAuthenticationException;
import net.paguo.domain.users.LocalUser;
import net.paguo.web.wicket.auth.UserView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

    protected LocalUser findSessionUser(){
        JournalWebSession session = (JournalWebSession) Session.get();
        final UserView authenticatedUser = session.getAuthenticatedUser();
        if (authenticatedUser != null){
            final String username = authenticatedUser.getUsername();
            try{
                return getUsersController().findByPermission(username);
            }catch (JournalAuthenticationException e){
                log.error("Cannot found permissions for user " + username);
                throw new RestartResponseAtInterceptPageException(Login.class);
            }
        }else{
            log.error("No authenticated user found in the middle of...");
            throw new RestartResponseAtInterceptPageException(Login.class);
        }
    }

    public SecuredWebPage(){
        super();
        verifyAccess();
        addLinks();

    }

    private void addLinks() {
        add(new BookmarkablePageLink("complaints", ComplaintsPage.class));
        add(new BookmarkablePageLink("problems", NetworkProblemsPage.class));
    }

    protected void verifyAccess(){
        if (! isUserAuthenticated()){
            log.debug("Authentication information not found. Redirecting to Login page");
            throw new RestartResponseAtInterceptPageException(Login.class);
        }
        AllowedRole declaredRole = getClass().getAnnotation(AllowedRole.class);
        if (declaredRole != null){
            if (! checkRole(declaredRole.value())){
                log.debug("Required authority not found: "
                        + declaredRole.value() + ". Redirecting to Login page");
                throw new RestartResponseAtInterceptPageException(Login.class);
            }
        }
    }

    private boolean checkRole(String s) {
        JournalWebSession session = (JournalWebSession) getSession();
        return session.isHasAuthority(s);
    }

    protected boolean isUserAuthenticated(){
        JournalWebSession session = (JournalWebSession) getSession();
        return session.isAuthenticated();
    }
}
