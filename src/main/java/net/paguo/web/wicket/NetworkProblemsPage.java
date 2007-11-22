package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.application.ApplicationSettings;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 0:17:11
 */
public class NetworkProblemsPage extends SecuredWebPage{
    private static final String ITEMS_PER_PAGE_KEY = "failureController.itemsPerPage";

    @SpringBean
    NetworkFailureController controller;

    @SpringBean
    ApplicationSettingsController settingsController;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public ApplicationSettingsController getSettingsController() {
        return settingsController;
    }

    public void setSettingsController(ApplicationSettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public NetworkProblemsPage() {
        final NetworkProblemListProvider provider = new NetworkProblemListProvider();
        ApplicationSettings itemsPerPageSettings = getSettingsController().findByKey(ITEMS_PER_PAGE_KEY);
        NetworkProblemDataView items;
        if (itemsPerPageSettings != null
                && !StringUtils.isEmpty(itemsPerPageSettings.getValue())
                && StringUtils.isNumeric(itemsPerPageSettings.getValue())){
            final Integer perPage = Integer.decode(itemsPerPageSettings.getValue());
            items = new NetworkProblemDataView
                    ("items", provider, perPage);
            items.setItemsPerPage(perPage);
        }else{
            items = new NetworkProblemDataView("items", provider);
        }
        add(HeaderContributor.forCss(NetworkProblemsPage.class, "wstyles.css"));
        add(items);
        add(new PagingNavigator("navigator", items));
        add(new FeedbackPanel("feedback"));
        add(new Link("create"){
            public void onClick() {
                setResponsePage(NetworkProblemCreatePage.class);
            }
        });
    }
}
class NetworkProblemDataView extends DataView {
    @SpringBean
    NetworkFailureController controller;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    NetworkProblemDataView(String id, IDataProvider dataProvider) {
        super(id, dataProvider);
    }

    public NetworkProblemDataView(String id, IDataProvider dataProvider, int itemsPerPage) {
        super(id, dataProvider, itemsPerPage);
    }


    protected void populateItem(Item item) {
        final NetworkProblem problem = (NetworkProblem) item.getModelObject();
        item.setModel(new CompoundPropertyModel(problem));
        item.add(new Label("id"));
        item.add(DateLabel.forDatePattern("failureTime", new Model(problem.getFailureTime()), "dd-MM-yy hh:mm"));
        item.add(new Label("failureDescription"));
        item.add(new Label("restoreAction.failureCause"));
        item.add(new Label("restoreAction.restoreAction"));
        item.add(DateLabel.forDatePattern("restoreAction.restoreTime", "dd-MM-yy hh:mm"));
        item.add(new Label("userCreated"));
        item.add(new Link("problemDetails"){
            public void onClick() {
                final PageParameters pageParameters = new PageParameters();
                pageParameters.add("problemId", String.valueOf(problem.getId()));
                setResponsePage(NetworkProblemCreatePage.class, pageParameters);
            }
        });
        final Link child = new Link("problemDelete") {
            public void onClick() {
                try {
                    getController().deleteProblem(problem);
                    String message = getLocalizer().getString("problem.deleted", NetworkProblemDataView.this);
                    Session.get().info(message);
                } catch (ControllerException e) {
                    Session.get().error("Cannot delete problem. Check for child complaints");
                }
            }
        };
        child.add(new SimpleAttributeModifier("onclick", "return confirm('Are you sure?');"));
        item.add(child);
    }
}
