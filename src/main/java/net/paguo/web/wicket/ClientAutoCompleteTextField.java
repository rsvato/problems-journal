package net.paguo.web.wicket;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.commons.lang.StringUtils;
import net.paguo.domain.clients.ClientItem;

import java.util.*;

/**
 * Client autocomplete text field.
 * TODO: use in ComplaintCreatePage too 
 */
public class ClientAutoCompleteTextField extends AutoCompleteTextField {
    private final Collection<ClientItem> clientItems;
    private static final long serialVersionUID = -8238608774119263059L;

    public ClientAutoCompleteTextField(Collection<ClientItem> clientItems, String id) {
        super(id);

        this.clientItems = clientItems;
    }

    public ClientAutoCompleteTextField(Collection<ClientItem> clientItems, String id, IModel model) {
        super(id, model);

        this.clientItems = clientItems;
    }

    protected Iterator getChoices(String input) {
        if (StringUtils.isEmpty(input)) {
            return Collections.EMPTY_LIST.iterator();
        }

        input = input.toLowerCase();

        List<String> result = new ArrayList<String>();
        for (ClientItem clientItem : clientItems) {
            final String clientName = clientItem.getClientName();
            if (!StringUtils.isEmpty(clientName)
                    && clientName.toLowerCase().startsWith(input.trim().toLowerCase())) {
                result.add(clientName);
            }
        }
        return result.iterator();
    }
}
