package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalUser;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * User: sreentenko
 * Date: 05.06.2007
 * Time: 0:13:52
 */
public class LocalUserListProvider implements IDataProvider {
    @SpringBean
    private UsersController controller;

    public LocalUserListProvider(UsersController controller) {
        //this.controller = controller;
        InjectorHolder.getInjector().inject(this);
    }

    public Iterator iterator(int first, int count) {
        UsersController controller = getUsersController();
        return controller.getUsersDao().readPart(count, first).iterator();
    }

    private UsersController getUsersController() {
        return controller;
    }

    public int size() {
        UsersController controller = getUsersController();
        return controller.getUsersDao().readAll().size();
    }

    public IModel model(Object o) {
        return new LocalUserModel((LocalUser) o, this.controller);
    }

    public void detach() {
    }
}
