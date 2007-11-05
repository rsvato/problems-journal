package net.paguo.web.wicket;

import net.paguo.domain.users.LocalUser;
import net.paguo.controller.UsersController;
import wicket.Component;
import wicket.Application;
import wicket.model.AbstractReadOnlyDetachableModel;
import wicket.model.IModel;

/**
 * User: sreentenko
 * Date: 05.06.2007
 * Time: 0:19:46
 */
public class LocalUserModel extends AbstractReadOnlyDetachableModel {
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

    @Override
    public IModel getNestedModel() {
        return null;
    }

    @Override
    protected void onAttach() {
        this.user = getUsersController().readUser(this.id);
    }

    @Override
    protected void onDetach() {
        this.user = null;
    }

    @Override
    protected Object onGetObject(Component component) {
        return this.user;
    }

    private UsersController getUsersController() {
        return this.controller;
    }

}
