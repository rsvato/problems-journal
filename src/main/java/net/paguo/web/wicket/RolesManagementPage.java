package net.paguo.web.wicket;

import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.users.LocalRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author Reyentenko
 */
@AuthorizeInstantiation({"ROLE_SUPERVISOR"})
public class RolesManagementPage extends SecuredWebPage {
    private static final Log log = LogFactory.getLog(RolesManagementPage.class);
    @SpringBean
    private UsersController controller;
    private static final long serialVersionUID = -432301675341906390L;

    public UsersController getController() {
        return controller;
    }

    public void setController(UsersController controller) {
        this.controller = controller;
    }

    public RolesManagementPage() {
        init(new LocalRole());
    }

    public RolesManagementPage(PageParameters parameters) {
        final LocalRole role = getUsersController().readRole(parameters.getInt("roleId"));
        init(role);
    }

    private void init(LocalRole localRole) {
        createRolesTable();
        add(new FeedbackPanel("feedback"));
        add(new LocalRoleForm("form", localRole));
        add(new Link("new"){
            private static final long serialVersionUID = -8796936053981934972L;

            public void onClick() {
                setResponsePage(RolesManagementPage.class);
            }
        });
        add(HeaderContributor.forCss(RolesManagementPage.class, "wstyles.css"));
    }

    private void createRolesTable(){
        final List<LocalRole> roles = (List<LocalRole>) getUsersController().getAllRoles();
        ListView view = new ListView("roles", roles){
            private static final long serialVersionUID = 1643044287867141914L;

            protected void populateItem(ListItem item) {
                final LocalRole role = (LocalRole) item.getModelObject();
                item.add(new Label("description", role.getRoleDescription()));
                item.add(new Label("role", role.getRole()));
                item.add(new Link("edit"){
                    private static final long serialVersionUID = 606621448685484564L;

                    public void onClick() {
                        PageParameters params = new PageParameters();
                        params.put("roleId", role.getId());
                        setResponsePage(RolesManagementPage.class, params);
                    }
                });
            }
        };
        add(view);
    }

    private void updateRole(LocalRole role) throws ControllerException {
        getUsersController().saveRole(role);
    }

    private final class LocalRoleForm extends Form {
        private static final long serialVersionUID = 8722261395530471262L;

        public LocalRoleForm(String s, LocalRole role) {
            super(s, new CompoundPropertyModel(role));
            add(new TextField("role"));
            add(new TextField("roleDescription"));
        }

        @Override
        protected void onSubmit() {
            LocalRole role = (LocalRole) getModelObject();
            try {
                updateRole(role);
                Session.get().info(getLocalizer().getString("success.role.update", RolesManagementPage.this));
            } catch (ControllerException e) {
                log.error(e);
                Session.get().error(getLocalizer().getString("error.role.update", RolesManagementPage.this));
            }
        }
    }
}
