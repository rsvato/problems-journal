package net.paguo.web.wicket;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: sreentenko
 * Date: 11.01.2008
 * Time: 1:36:29
 */
public abstract class SecuredWebMarkupContainer extends WebMarkupContainer {
    private static final Log log = LogFactory.getLog(ComplaintChangeFragment.class);

    public SecuredWebMarkupContainer(String id) {
        super(id);
    }

    @Override
    public boolean isVisible() {
       return super.isVisible() && verifyAccess();
    }

    protected boolean verifyAccess(){
        if (! isUserAuthenticated()){
            log.debug("Authentication information not found.");
            return false;
        }
        AllowedRole declaredRole = getClass().getAnnotation(AllowedRole.class);
        return declaredRole != null && checkRole(declaredRole.value());
    }

    protected boolean checkRole(String s) {
        JournalWebSession session = (JournalWebSession) getSession();
        return session.isHasAuthority(s);
    }

    protected boolean isUserAuthenticated(){
        JournalWebSession session = (JournalWebSession) getSession();
        return session.isAuthenticated();
    }
}
