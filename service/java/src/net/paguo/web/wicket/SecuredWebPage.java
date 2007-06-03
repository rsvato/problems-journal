package net.paguo.web.wicket;

import wicket.RestartResponseAtInterceptPageException;

/**
 * User: sreentenko
 * Date: 04.06.2007
 * Time: 0:52:35
 */
public class SecuredWebPage extends ApplicationWebPage {
    public SecuredWebPage(){
        super();

    }

    protected void verifyAccess(){
        if (! isUserAuthenticated()){
            throw new RestartResponseAtInterceptPageException(Login.class);
        }
        AllowedRole declaredRole = getClass().getAnnotation(AllowedRole.class);
        if (declaredRole != null){
            if (! checkRole(declaredRole.value())){
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
