package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.domain.users.ApplicationRole;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValueConversionException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: sreentenko
 * Date: 20.01.2008
 * Time: 23:57:35
 */
public class ApplicationRolesPage extends SecuredWebPage{

    @SpringBean
    private UsersController controller;

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public UsersController getController() {
        return controller;
    }

    public ApplicationRolesPage(PageParameters parameters) {
        ApplicationRole local;
        try{
            Integer roleId = parameters.getInt("roleId");
            local = getController().getRole(roleId);
        }catch(StringValueConversionException e){
            local = new ApplicationRole();
        }

        add(new FeedbackPanel("feedback"));
        add(new ApplicationRoleForm("form", local));

        final List<ApplicationRole> roles = getController().getApplicationRoles();
        Collections.sort(roles, new Comparator<ApplicationRole>(){
            public int compare(ApplicationRole applicationRole, ApplicationRole applicationRole1) {
                if (applicationRole.getClass().getName().equals(applicationRole1.getClass().getName())){
                    return applicationRole.getAction().compareTo(applicationRole1.getAction());
                }
                return applicationRole.getClass().getName().compareTo(applicationRole1.
                        getClass().getName());
            }
        });
        ListView table = new ListView("table", roles){
            protected void populateItem(ListItem item) {
                final ApplicationRole role = (ApplicationRole) item.getModelObject();
                item.add(new Label("className", role.getClassName()));
                item.add(new Label("action", role.getAction().name()));
                item.add(new Label("role", role.getRole()));
                item.add(new Label("roleDescription", role.getRoleDescription()));
                item.add(new Link("edit"){
                    public void onClick() {
                        PageParameters parameters = new PageParameters();
                        parameters.put("roleId", role.getId());
                        setResponsePage(ApplicationRolesPage.class, parameters);
                    }
                });
                final Link child = new Link("delete") {
                    public void onClick() {
                        try{
                            getController().deleteApplicationRole(role);
                            setResponsePage(ApplicationRolesPage.class);
                        }catch (ControllerException e){
                            Session.get().error(e.getMessage());
                        }
                    }
                };
                item.add(child);
            }
        };
        add(table);
        add(HeaderContributor.forCss(ApplicationRolesPage.class, "wstyles.css"));
    }

    public boolean saveRole(ApplicationRole role){
        try {
            getController().saveRole(role);
            Session.get().info("Role successfully added or updated");
            return true;
        } catch (ControllerException e) {
            Session.get().error(e.getMessage());
            return false;
        }
    }

    private class ApplicationRoleForm extends Form {
        public ApplicationRoleForm(String s, ApplicationRole local) {
            super(s);
            setModel(new CompoundPropertyModel(local));
            final boolean newRole = local.getId() == null;
            add(new DropDownChoice("className", Arrays.asList(ClientComplaint.class.getName(),
                    NetworkProblem.class.getName())).setRequired(newRole).setEnabled(newRole));
            add(new DropDownChoice("action", Arrays.asList(ApplicationRole.Action.values())).
                    setRequired(newRole).setEnabled(newRole));
            add(new TextField("role").setRequired(true));
            add(new TextField("roleDescription").setRequired(true));
        }

        @Override
        public void onSubmit(){
            ApplicationRole role = (ApplicationRole) getModelObject();
            if (saveRole(role)){
                setResponsePage(ApplicationRolesPage.class);
            }
        }
    }
}
