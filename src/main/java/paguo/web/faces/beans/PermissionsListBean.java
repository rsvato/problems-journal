package net.paguo.web.faces.beans;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalUser;

import java.util.List;
import java.util.Collection;

import org.apache.myfaces.trinidad.component.core.data.CoreTable;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

/**
 * User: slava
 * Date: 28.12.2006
 * Time: 1:06:27
 * Version: $Id$
 */
public class PermissionsListBean extends BaseBean {
    private UsersController controller;

    private CoreTable users;


    public CoreTable getUsers() {
        return users;
    }

    public void setUsers(CoreTable users) {
        this.users = users;
    }

    public UsersController getController() {
        return controller;
    }

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public CollectionModel getAllUsers(){
        Collection<LocalUser> users = getController().getAll();
        return ModelUtils.toCollectionModel(users);
    }
}
