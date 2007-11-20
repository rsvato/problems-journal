package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.JournalAuthenticationException;
import net.paguo.web.wicket.auth.UserView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 31.05.2007
 * Time: 1:02:58
 */
public class Login extends ApplicationWebPage{
    public static final Log log = LogFactory.getLog(Login.class);

    @SpringBean
    private UsersController usersController;

    private TextField userId;
    private PasswordTextField password;

    public Login(){
        userId = new TextField("userId", new PropertyModel(this, "userId"));
        password = new PasswordTextField("password", new PropertyModel(this, "password"));
        userId.setRequired(true);
        password.setRequired(true);
        Form form = new LoginForm("loginForm");
        form.add(userId);
        form.add(password);
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
        add(form);
    }

    public String getUserId(){
        return userId.getInput();
    }

    public String getPassword(){
        return password.getInput();
    }

    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }

    final class LoginForm extends Form{

        public LoginForm(String s) {
            super(s);
        }

        @Override
        protected void onSubmit() {
            log.debug("Login attempt: " + getUserId() + " " + getPassword());
            JournalWebSession session = (JournalWebSession) getSession();
            if (session.isAuthenticated()){
                String errorMessage = getLocalizer().getString("error.already.authenticated", Login.this);
                error(errorMessage);
                return;
            }
            try {

                UserView authenticated = getUsersController().authenticate(getUserId(), getPassword());
                session.setAuthenticatedUser(authenticated);
                if (! continueToOriginalDestination()){
                    ApplicationWebPage next = new Dashboard();
                    next.setUserName(getUserId());
                    setResponsePage(next);
                }
            } catch (JournalAuthenticationException e) {
                log.error(e);
                String errorMessage = getLocalizer()
                        .getString("error.invalid.credentials", Login.this,
                                "Unable to log on");
                error(errorMessage);
            }
        }

    }


}
