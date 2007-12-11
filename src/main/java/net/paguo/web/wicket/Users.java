package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.users.LocalUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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
public class Users extends ApplicationWebPage {

    private static final Log log = LogFactory.getLog(Users.class);

    @SpringBean
    UsersController controller;

    public UsersController getController() {
        return controller;
    }

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public Users(){
        init(new LocalUser());
    }

    public Users(PageParameters parameters){
        final int userId = parameters.getInt("userId");
        LocalUser search;
        search = getController().findUser(userId);
        if (search == null){
            log.error("Nonexistent user id " + userId);
            search = new LocalUser();
        }
        init(search);
    }

    private void init(LocalUser user) {
        final LocalUserListProvider provider = new LocalUserListProvider(getController());
        DataView users = new UsersDataView("users", provider);
        add(users);
        add(createForm(user));
        add(new FeedbackPanel("feedback"));
        add(HeaderContributor.forCss(Users.class, "wstyles.css"));
    }

    private UserForm createForm(LocalUser user) {
        return new UserForm("form", user);
    }

    private class UserForm extends Form{

        public UserForm(String id, LocalUser user) {
            super(id, new CompoundPropertyModel(user));
            add(new TextField("permissionEntry.userName").setRequired(true));
            add(new PasswordTextField("permissionEntry.plainPassword").setRequired(user.getId() == null));

            add(new TextField("personalData.name").setRequired(true));
            add(new TextField("personalData.familyName").setRequired(true));
            add(new TextField("personalData.parentName"));

            add(new TextField("contactData.email").setRequired(true));
            add(new TextField("contactData.phone"));
            add(new TextField("contactData.mobilePhone"));
            add(new TextField("contactData.workPhone"));
            add(new TextArea("contactData.contactComments"));

            final CheckBoxMultipleChoice choice = new CheckBoxMultipleChoice("groups", getController().getAllGroups());
            choice.setChoiceRenderer(new ChoiceRenderer("groupName", "id"));
            add(choice);
        }

        public void onSubmit(){
            LocalUser submitted = (LocalUser) getModelObject();
            if (! StringUtils.isEmpty(submitted.getPermissionEntry().getPlainPassword())){
                submitted.getPermissionEntry().setDigest(DigestUtils.shaHex(submitted.
                        getPermissionEntry().getPlainPassword()));
            }

            try {
                getController().saveOrUpdateUser(submitted);
                Session.get().info(getLocalizer().getString("user.updated", Users.this));
            } catch (ControllerException e) {
                log.error(e);
                Session.get().error(getLocalizer().getString("user.update.error", Users.this));

            }
        }
    }

    private class UsersDataView extends DataView{
        public UsersDataView(String s, IDataProvider iDataProvider) {
            super(s, iDataProvider);
        }

        protected void populateItem(Item item) {
            final LocalUser user = (LocalUser) item.getModelObject();
            item.setModel(new CompoundPropertyModel(user));
            item.add(new Label("personalData.name"));
            item.add(new Label("personalData.familyName"));
            item.add(new Label("permissionEntry.userName"));
            item.add(new Label("contactData.email"));
            item.add(new Link("edit"){
                public void onClick() {
                    PageParameters parameters = new PageParameters();
                    parameters.put("userId", user.getId());
                    setResponsePage(Users.class, parameters);
                }
            });

            Link deleteLink = new Link("delete"){
                public void onClick() {
                    try{
                        getController().deleteUser(user);
                        Session.get().info(getLocalizer().getString("user.deleted", Users.this));
                    } catch (ControllerException e) {
                        log.error(e);
                        Session.get().error(getLocalizer().getString("user.delete.error", Users.this));
                    }
                    setResponsePage(Users.class);
                }
            };

            deleteLink.add(new SimpleAttributeModifier(
                        "onclick", "return confirm('Are you sure?');"));

            item.add(deleteLink);
        }
    }
}
