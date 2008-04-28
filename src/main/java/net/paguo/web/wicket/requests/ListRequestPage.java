package net.paguo.web.wicket.requests;

import net.paguo.web.wicket.SecuredWebPage;
import org.apache.wicket.behavior.HeaderContributor;

/**
 * @author Reyentenko
 */
public class ListRequestPage extends SecuredWebPage {
    private static final long serialVersionUID = 6601552429928651832L;

    public ListRequestPage() {
        add(new RequestListPanel("panel", null));
        add(HeaderContributor.forCss(SecuredWebPage.class, "wstyles.css"));
    }
}
