package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalGroup;
import net.paguo.domain.users.LocalRole;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

/**
 * User: sreentenko
 * Date: 04.12.2007
 * Time: 23:41:20
 */
public class GroupPage extends ApplicationWebPage {
    @SpringBean
    private UsersController controller;

    public UsersController getController() {
        return controller;
    }

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public GroupPage() {
        add(new GroupForm("form", new LocalGroup()));

    }

    public GroupPage(PageParameters parameters) {
        final int groupId = parameters.getInt("groupId");
        final LocalGroup group = getController().getGroup(groupId);
        add(new GroupForm("form", group));
    }

    void updateLocalGroup(LocalGroup group) {
        if (group.getId() == null) {
            getController().createGroup(group);
        } else {
            getController().updateGroup(group);
        }

    }

    private class GroupForm extends Form {

        private CheckGroup grp;

        private GroupForm(String id, final LocalGroup group) {
            super(id, new CompoundPropertyModel(group));
            add(new TextField("groupName").setRequired(true));
            grp = new CheckGroup("roles", new ArrayList());
            CheckGroupSelector selector = new CheckGroupSelector("selector");
            grp.add(selector);

            //TODO: generic ListView with preselected collection.

            ListView roles = new ListView("rolesList",
                    (List<LocalRole>) getController().getAllRoles()) {
                protected void populateItem(ListItem item) {
                    final LocalRole localRole = (LocalRole) item.getModelObject();
                    final Check child = new Check("check", item.getModel());
                    item.add(child);
                    item.add(new Label("descr", new PropertyModel(localRole, "roleDescription")));
                    item.add(new Label("role", new PropertyModel(localRole, "role")));
                }
            };
            grp.add(roles);
            add(grp);
        }

        @Override
        protected void onSubmit() {
            LocalGroup group = (LocalGroup) getModelObject();
            if (grp != null){
                final Collection o = (Collection) grp.getModelObject();
                group.setRoles(new HashSet(o));
            }
            updateLocalGroup(group);
        }
    }

    private class GroupCheck extends Check{

        private Boolean preselect;

        public GroupCheck(String id, IModel model, Boolean preselect) {
            super(id, model);
            this.preselect = preselect;
        }

        public Boolean getPreselect() {
            return preselect;
        }

        public void setPreselect(Boolean preselect) {
            this.preselect = preselect;
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            if (preselect){
                tag.put("checked", "checked");
            }
        }
    }
}
