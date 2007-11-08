package net.paguo.controller;

import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.UserPermission;
import net.paguo.domain.common.PersonalData;
import net.paguo.domain.common.ContactData;
import net.paguo.dao.ClientItemDAO;
import net.paguo.controller.exception.ControllerException;

import java.util.Collection;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


/**
 * @version $Id $
 */
public class ClientItemController implements Controller<ClientItem> {
    private ClientItemDAO clientDao;

    public net.paguo.dao.ClientItemDAO getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientItemDAO clientDao) {
        this.clientDao = clientDao;
    }

    @Deprecated
    public Collection<ClientItem> getClients(){
        List<ClientItem> lst = getClientDao().readAll();
        Collections.sort(lst, new Comparator<ClientItem>(){
            public int compare(ClientItem o1, ClientItem o2) {
                return o1.getClientName().compareTo(o2.getClientName());
            }
        });
        return lst;
    }

    public Collection<ClientItem> getAllClients(){
        return getClientDao().findActive();
    }

    public ClientItem readClient(Integer id){
        return getClientDao().read(id);
    }


    public Integer create(ClientItem newInstance) {
        return clientDao.create(newInstance);
    }

    public void update(ClientItem transientObject) {
        clientDao.update(transientObject);
    }

    public void delete(ClientItem persistentObject) {
        clientDao.delete(persistentObject);
    }

    public LocalUser createNew(){
        LocalUser result = new LocalUser();
        result.setPersonalData(new PersonalData());
        result.setPermissionEntry(new UserPermission());
        result.setContactData(new ContactData());
        return result;
    }

    public Collection<ClientItem> getAll() {
        List<ClientItem> lst = getClientDao().readAll();
        Collections.sort(lst, new Comparator<ClientItem>() {
            public int compare(ClientItem o1, ClientItem o2) {
                return o1.getClientName().compareTo(o2.getClientName());
            }
        });
        return lst;
    }

    public void save(ClientItem item) throws ControllerException {
        try{
        if (item.getId() == null){
            create(item);
        }else{
            update(item);
        }
        }catch(Throwable t){
            throw new ControllerException(t);
        }
    }
}
