package net.paguo.web.wicket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
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
        Form form = new LoginForm("loginForm");
        form.add(userId);
        form.add(password);
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
             log.debug("Login attempt: " + getUserId());
             log.debug("Unsuccessful...");
        }
    }

}
