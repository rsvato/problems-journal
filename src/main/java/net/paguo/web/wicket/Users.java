package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalUser;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 05.06.2007
 * Time: 0:38:23
 */
@AllowedRole("ROLE_ADMINUSERS")
public class Users extends SecuredWebPage {
    @SpringBean
    UsersController controller;

    public UsersController getController() {
        return controller;
    }

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public Users(){
        final LocalUserListProvider provider = new LocalUserListProvider(getController());
        DataView users = new UsersDataView("users", provider);
        add(users);
    }

}
class UsersDataView extends DataView{
    public UsersDataView(String s, IDataProvider iDataProvider) {
        super(s, iDataProvider);
    }

    protected void populateItem(Item item) {
        LocalUser user = (LocalUser) item.getModelObject();
        item.setModel(new CompoundPropertyModel(user));
        item.add(new Label("personalData.name"));
        item.add(new Label("personalData.familyName"));
        item.add(new Label("permissionEntry.userName"));
        item.add(new Label("contactData.email"));
    }
}
