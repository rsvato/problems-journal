package net.paguo.web.wicket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

/**
 * User: sreentenko
 * Date: 04.06.2007
 * Time: 0:52:35
 */
public class SecuredWebPage extends ApplicationWebPage {
    private static final Log log = LogFactory.getLog(SecuredWebPage.class);
    public SecuredWebPage(){
        super();
        verifyAccess();

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
