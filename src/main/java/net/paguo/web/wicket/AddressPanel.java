package net.paguo.web.wicket;

import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.clients.PostalAddress;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: sreentenko
 * Date: 03.03.2008
 * Time: 1:45:42
 */
public class AddressPanel extends Panel {

    public AddressPanel(String id, ClientItem cl) {
        super(id);
        List<PostalAddress> pa = Collections.emptyList();
        if (cl != null) {
            pa = new ArrayList<PostalAddress>();
            pa.addAll(cl.getAddresses());
        }
        add(new ListView("addresses", pa) {
            protected void populateItem(ListItem item) {
                PostalAddress postalAddress = (PostalAddress) item.getModel();
                item.add(new Label("addressLbl", postalAddress.toString()));
            }
        });
    }
}
