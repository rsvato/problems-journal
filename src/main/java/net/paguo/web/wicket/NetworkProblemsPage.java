package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.application.ApplicationSettings;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
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
    }
}
class NetworkProblemDataView extends DataView {
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
        item.add(new Label("failureTime"));
        item.add(new Label("failureDescription"));
        item.add(new Label("restoreAction.failureCause"));
        item.add(new Label("restoreAction.restoreAction"));
        item.add(new Label("restoreAction.restoreTime"));
    }
}
