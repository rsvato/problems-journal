package net.paguo.web.wicket;

import net.paguo.web.wicket.auth.Authority;
import net.paguo.web.wicket.auth.UserView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;

import java.util.Set;

/**
 * User: sreentenko
 * Date: 02.06.2007
 * Time: 0:55:45
 */
public class JournalWebSession extends WebSession {
    private static final Log log = LogFactory.getLog(JournalWebSession.class);
    private UserView authenticatedUser;

    public JournalWebSession(WebApplication webApplication, Request request) {
        super(request);
    }

    public UserView getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(UserView authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    public boolean isUserHasRoles() {
        return isAuthenticated() && authenticatedUser.getAuthorities() != null &&
                authenticatedUser.getAuthorities().size() > 0;
    }

    public boolean isHasAuthority(String authority) {
        log.debug("Searching authority " + authority);
        boolean result = false;
        if (isUserHasRoles()) {
            for (Authority o : authenticatedUser.getAuthorities()) {
                if (authority.equals(o.getAuthority())) {
                    log.debug("Authority found");
                    result = true;
                    break;
                }

            }
        } else {
            log.debug("User is not authenticated or does not have any permission");
        }
        return result;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        authenticatedUser = null;
    }

    public Roles getRoles(){
        final Set<Authority> set = authenticatedUser.getAuthorities();
        String[] rolesList = new String[set.size()];
        int i = 0;
        for (Authority authority : set){
           rolesList[i++] = authority.getAuthority();
        }
        return new Roles(rolesList);
    }

    public static JournalWebSession get(){
        return (JournalWebSession) Session.get();
    }

}
