package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.users.LocalGroup;
import net.paguo.domain.users.LocalRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * User: sreentenko
 * Date: 04.12.2007
 * Time: 23:41:20
 */
@AllowedRole("ROLE_SUPERVISOR")
public class GroupPage extends SecuredWebPage {
    private static final Log log = LogFactory.getLog(GroupPage.class);
    @SpringBean
    private UsersController controller;

    public UsersController getController() {
        return controller;
    }

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public GroupPage() {
        init(new LocalGroup());
    }

    private void createGroupTable() {
        final List<LocalGroup> groups = getController().getAllGroups();
        ListView view = new ListView("view", groups){
            protected void populateItem(ListItem item) {
                final LocalGroup grp = (LocalGroup) item.getModelObject();
                item.add(new Label("gn", new PropertyModel(grp, "groupName")));
                item.add(new Link("edit"){
                    public void onClick() {
                        final Long groupId = grp.getId();
                        PageParameters parameters = new PageParameters();
                        parameters.put("groupId", groupId);
                        setResponsePage(GroupPage.class, parameters);
                    }
                });
                final Link child = new Link("delete") {
                    public void onClick() {
                        try {
                            getController().deleteGroup(grp);
                            String message = getLocalizer().getString("group.deleted", GroupPage.this);
                            Session.get().info(message);
                        } catch (ControllerException e) {
                            String message = getLocalizer().getString("group.delete.error", GroupPage.this);
                            Session.get().error(message);
                        }
                        setResponsePage(GroupPage.class);
                    }
                };
                child.add(new SimpleAttributeModifier(
                        "onclick", "return confirm('Are you sure?');"));
                item.add(child);
            }
        };
        add(view);
    }

    public GroupPage(PageParameters parameters) {
        final int groupId = parameters.getInt("groupId");
        LocalGroup group = getController().getGroup(groupId);
        if (group == null) group = new LocalGroup();
        init(group);
    }

    private void init(LocalGroup group) {
        createForm(group);
        createGroupTable();
        add(new FeedbackPanel("feedback"));
        add(new Link("new"){
            public void onClick() {
                setResponsePage(GroupPage.class);
            }
        });
        add(HeaderContributor.forCss(GroupPage.class, "wstyles.css"));
    }

    private void createForm(LocalGroup group) {
        add(new GroupForm("form", group));
    }

    void updateLocalGroup(LocalGroup group) throws ControllerException{
        if (group.getId() == null) {
            getController().createGroup(group);
        } else {
            getController().updateGroup(group);
        }

    }

    private class GroupForm extends Form {

        private GroupForm(String id, final LocalGroup group) {
            super(id, new CompoundPropertyModel(group));
            add(new TextField("groupName").setRequired(true));
            final List<LocalRole> list = (List<LocalRole>) getController().getAllRoles();
            add(new CheckBoxMultipleChoice("rolesChecks", new PropertyModel(group, "roles"),
                    list));
            add(new CheckBoxMultipleChoice("aprolesChecks", new PropertyModel(group, "applicationRoles"),
                    getController().getApplicationRoles()));
        }

        @Override
        protected void onSubmit() {
            LocalGroup group = (LocalGroup) getModelObject();
            try{
                updateLocalGroup(group);
                Session.get().info(getLocalizer().getString("group.updated", GroupPage.this));
                setResponsePage(GroupPage.class);
            }catch (ControllerException e){
                log.error(e);
                Session.get().error(getLocalizer().getString("error.group.update", GroupPage.this));
            }
        }
    }
}
