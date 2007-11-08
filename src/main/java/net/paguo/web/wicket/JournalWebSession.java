package net.paguo.web.wicket;

import net.paguo.web.wicket.auth.Authority;
import net.paguo.web.wicket.auth.UserView;
import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

/**
 * User: sreentenko
 * Date: 02.06.2007
 * Time: 0:55:45
 */
public class JournalWebSession extends WebSession {
    private UserView authenticatedUser;

    public JournalWebSession(WebApplication webApplication) {
        super(webApplication);
    }

    public UserView getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(UserView authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public boolean isAuthenticated(){
        return authenticatedUser != null;
    }

    public boolean isUserHasRoles(){
        return isAuthenticated() && authenticatedUser.getAuthorities() != null &&
                authenticatedUser.getAuthorities().size() > 0;
    }

    public boolean isHasAuthority(String authority){
        return isUserHasRoles() && authenticatedUser.getAuthorities().contains(new Authority(authority));
    }
}
