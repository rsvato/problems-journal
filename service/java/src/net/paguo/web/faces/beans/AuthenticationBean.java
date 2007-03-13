package net.paguo.web.faces.beans;

import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.Authentication;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User: slava
 * Date: 25.12.2006
 * Time: 0:13:21
 * Version: $Id$
 */
public class AuthenticationBean extends BaseBean{
    private AuthenticationManager manager;
    private Authentication auth;
    private String username;
    private String password;
    private User user;
    private static final String FAILURE = "failure";
    private static final Log log = LogFactory.getLog(AuthenticationBean.class);


    public AuthenticationManager getManager() {
        return manager;
    }

    public void setManager(AuthenticationManager manager) {
        this.manager = manager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Authentication getAuth() {
        return auth;
    }

    public void setAuth(Authentication auth) {
        this.auth = auth;
    }

    public String authenticate() {
        String outcome = "success";
        HttpServletRequest request = getRequest();

        try {
            String userName = getUsername();
            String password = getPassword();
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userName, password);

            authReq.setDetails(new WebAuthenticationDetails(request));

            HttpSession session = request.getSession();
            session.setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, userName);
            log.debug("Before authentication procedure");
            Authentication auth = getManager().authenticate(authReq);
            SecurityContext sessionSecCtx = (SecurityContext) session
                    .getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
            log.debug("SecurityContext from session  " + (sessionSecCtx != null ? sessionSecCtx.toString() : "null"));
            SecurityContext secCtx = SecurityContextHolder.getContext();
            log.debug("SecurityContext from holder " +  (secCtx != null ? secCtx.toString() : "null"));
            secCtx.setAuthentication(auth);
            log.debug("placing SecurityContext from holder into session");
            session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, secCtx);

            Utils.createValueBinding("#{authentication.auth}", auth);

            String urlKey = AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY;
            if (urlKey != null) {
                log.error("urlKey found");
                SavedRequest savedRequest = ((SavedRequest) session.getAttribute(urlKey));
                if (savedRequest != null) {
                    String targetUrl = savedRequest.getRequestURL();
                    session.removeAttribute(urlKey);

                    String ctxPath = request.getContextPath();
                    int idx = targetUrl.indexOf(ctxPath);
                    String target = targetUrl.substring(idx + ctxPath.length());
                    log.error("authentication successful, forwarding to " + target + " obtained from " + targetUrl);


                    //forward(target);
                }
            } else {
                outcome = "root";
            }
        }
        catch (AuthenticationException e) {
            outcome = FAILURE;
            log.error(e);
            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        log.error("And finally outcome is " + outcome);
        return outcome;
    }

    public String logout() {
        log.debug("Logout requested");
        Utils.createValueBinding("#{authentication.auth}", null);
        SecurityContext secCtx = SecurityContextHolder.getContext();
        if (secCtx != null && secCtx.getAuthentication() != null) {
            log.debug("Emptying security context");
            secCtx.setAuthentication(null);
            HttpSession session = getRequest().getSession();
            if (session != null)
                session.removeAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
        }
        log.debug("Finished logout procedure");
        return OUTCOME_LOGOUT;
    }


}
