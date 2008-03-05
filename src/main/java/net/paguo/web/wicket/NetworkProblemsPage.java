package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.search.controller.NetworkProblemSearchController;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import static net.paguo.domain.users.ApplicationRole.Action.*;

/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 0:17:11
 */
public class NetworkProblemsPage extends FailurePage<NetworkProblem>{

    @SpringBean
    NetworkProblemSearchController searchController;

    public NetworkProblemsPage(PageParameters parameters) {
        super(parameters);
    }


    public NetworkProblemSearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(NetworkProblemSearchController searchController) {
        this.searchController = searchController;
    }



    public NetworkProblemsPage() {
        super();
    }

    protected void initConstantCompoments() {
        add(HeaderContributor.forCss(NetworkProblemsPage.class, "wstyles.css"));
        add(new FeedbackPanel("feedback"));
        final BookmarkablePageLink createLink = new BookmarkablePageLink("create", NetworkProblemCreatePage.class);
        secureElement(createLink, NetworkProblem.class, CREATE);
        add(createLink);
        add(new ProblemSearchForm("search"));
        add(new Link("reindex") {
            public void onClick() {
                getSearchController().reindex();
            }
        });
    }

    protected void initDefault(IDataProvider provider) {
        DataView items;
        int perPage = getPerPageSettings();

        String itemsId = "items";
        if (perPage > 0) {
            items = new NetworkProblemDataView
                    (itemsId, provider, perPage);
            items.setItemsPerPage(perPage);
        } else {
            items = new NetworkProblemDataView(itemsId, provider);
        }
        add(items);
        add(new PagingNavigator("navigator", items));
    }

    protected SearchListProvider<NetworkProblem> getSearchProvider(String s) {
        return new SearchListProvider<NetworkProblem>(getSearchController(), s);
    }

    protected IDataProvider getDefaultProvider() {
        return new NetworkProblemListProvider();  //To change body of implemented methods use File | Settings | File Templates.
    }

    private class NetworkProblemDataView extends DataView {
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
            item.add(DateLabel.forDatePattern("failureTime",
                    new Model(problem.getFailureTime()), "dd-MM-yy HH:mm"));
            item.add(new Label("failureDescription"));
            item.add(new Label("restoreAction.failureCause"));
            item.add(new Label("restoreAction.restoreAction"));
            item.add(DateLabel.forDatePattern("restoreAction.restoreTime", "dd-MM-yy HH:mm"));
            item.add(new Label("userCreated"));
            final Link pageLink
                    = new BookmarkablePageLink("problemDetails", NetworkProblemCreatePage.class)
                    .setParameter("problemId", problem.getId());

            item.add(pageLink);
            secureElement(pageLink, NetworkProblem.class, CREATE, OVERRIDE);
            final Link child = new Link("problemDelete") {
                public void onClick() {
                    try {
                        getController().deleteProblem(problem);
                        String message = getLocalizer().getString("problem.deleted",
                                NetworkProblemDataView.this);
                        Session.get().info(message);
                    } catch (ControllerException e) {
                        Session.get().error("Cannot delete problem. Check for child complaints");
                    }
                }
            };
            secureElement(child, NetworkProblem.class, DELETE);
            child.add(new SimpleAttributeModifier("onclick", "return confirm('Are you sure?');"));
            item.add(child);
        }
    }
}
