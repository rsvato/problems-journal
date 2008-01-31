package net.paguo.web.wicket;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;


/**
 * User: sreentenko
 * Date: 01.06.2007
 * Time: 0:44:20
 */
public class ApplicationWebPage extends WebPage {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ApplicationWebPage() {
        final Link logoutLink = new Link("logout") {
            public void onClick() {
                final Session session = Session.get();
                session.invalidate();
                setResponsePage(Login.class);
            }
        };

        final Link loginLink = new Link("login"){
           public void onClick(){
               setResponsePage(Login.class);
           }
        };

        loginLink.setVisible(false);
        final JournalWebSession session = (JournalWebSession) Session.get();
        if (session == null || ! session.isAuthenticated()){
            logoutLink.setVisible(false);
            loginLink.setVisible(true);
        }

        add(loginLink);
        add(logoutLink);
    }

    protected void addLinks() {
        final Link logoutLink = new Link("logout") {
            public void onClick() {
                Session.get().invalidate();
                setResponsePage(Login.class);
            }
        };

        final Link loginLink = new BookmarkablePageLink("login", Login.class);

        loginLink.setVisible(false);
        final JournalWebSession session = (JournalWebSession) Session.get();
        if (session == null || ! session.isAuthenticated()){
            logoutLink.setVisible(false);
            loginLink.setVisible(true);
        }

        add(loginLink);
        add(logoutLink);

    }
}
