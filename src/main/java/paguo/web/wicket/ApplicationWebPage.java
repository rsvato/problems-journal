package net.paguo.web.wicket;

import wicket.markup.html.WebPage;

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
}
