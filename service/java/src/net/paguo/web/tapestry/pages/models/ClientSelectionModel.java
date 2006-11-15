package net.paguo.web.tapestry.pages.models;

import org.apache.tapestry.form.IPropertySelectionModel;
import net.paguo.domain.clients.ClientItem;
import net.paguo.controller.ClientItemController;

import java.util.Collection;
import java.util.List;

/**
 * User: slava
* Date: 15.11.2006
* Time: 0:58:35
* Version: $Id$
*/
public class ClientSelectionModel implements IPropertySelectionModel {

    private Collection<ClientItem> clients;

    public ClientSelectionModel(Collection<ClientItem> items){
       this.clients = items;
    }

    public int getOptionCount() {
        return clients.size();
    }

    public Object getOption(int index) {
        return ((List) clients).get(index);
    }

    public String getLabel(int index) {
        Object result = getOption(index);
        if (result != null)
            return ((ClientItem) result).getClientName();
        return null;
    }

    public String getValue(int index) {
        Object result = getOption(index);
        return String.valueOf(((ClientItem) result).getId());
    }

    public Object translateValue(String value) {
        return Integer.decode(value);
    }
}
