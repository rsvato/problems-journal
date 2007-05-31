package net.paguo.web.wicket;

import wicket.protocol.http.WebApplication;

/**
 * User: sreentenko
 * Date: 30.05.2007
 * Time: 0:51:30
 */
public class JournalApplication extends WebApplication {
    @Override
    public Class getHomePage() {
        return Dashboard.class;
    }

    @Override
    protected void init() {
        mountBookmarkablePage("/login", Login.class);
        mountBookmarkablePage("/dashboard", Dashboard.class);
    }
}
