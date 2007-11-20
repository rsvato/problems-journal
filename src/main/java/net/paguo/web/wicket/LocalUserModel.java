package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalUser;
import org.apache.wicket.model.AbstractReadOnlyModel;
                 
/**
 * User: sreentenko
 * Date: 05.06.2007
 * Time: 0:19:46
 */
public class LocalUserModel extends AbstractReadOnlyModel {
    private final Integer id;
    private transient LocalUser user;
    private UsersController controller;

    protected LocalUserModel(Integer id){
        if (id == null){
            throw new IllegalArgumentException("Id should not be null");
        }
        this.id = id;
    }

    protected LocalUserModel(LocalUser user){
        this(user.getId());
        this.user = user;
    }

    public LocalUserModel(LocalUser localUser, UsersController controller) {
        this(localUser);
        this.controller = controller;
    }


    private UsersController getUsersController() {
        return this.controller;
    }

    public Object getObject() {
        return this.user;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
