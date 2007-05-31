package net.paguo.web.wicket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wicket.Localizer;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * User: sreentenko
 * Date: 31.05.2007
 * Time: 1:02:58
 */
public class Login extends ApplicationWebPage{
    public static final Log log = LogFactory.getLog(Login.class);

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

    final class LoginForm extends Form{


        public LoginForm(String s) {
            super(s);
        }

        @Override
        protected void onSubmit() {
             log.debug("Login attempt: " + getUserId() + " " + getPassword());
             log.debug("Unsuccessful...");
            if (false){
                ApplicationWebPage next = new Dashboard();
                next.setUserName(getUserId());
                setResponsePage(next);
            }else{
                String errorMessage = getLocalizer()
                        .getString("error.invalid.credentials", Login.this,
                        "Unable to log on");
                error(errorMessage);

            }
        }
    }

}
