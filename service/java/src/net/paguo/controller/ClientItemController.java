package net.paguo.controller;

import net.paguo.domain.clients.ClientItem;
import net.paguo.dao.ClientItemDAO;

import java.util.Collection;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * @version $Id $
 */
public class ClientItemController {
    private ClientItemDAO clientDao;

    public net.paguo.dao.ClientItemDAO getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientItemDAO clientDao) {
        this.clientDao = clientDao;
    }

    public Collection<ClientItem> getAllClients(){
        List<ClientItem> lst = getClientDao().readAll();
        Collections.sort(lst, new Comparator<ClientItem>(){
            public int compare(ClientItem o1, ClientItem o2) {
                return o1.getClientName().compareTo(o2.getClientName());
            }
        });
        return lst;
    }
}
