package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.common.PersonalData;
import net.paguo.domain.problems.ClientComplaint;
import static net.paguo.domain.users.ApplicationRole.Action.*;
import net.paguo.domain.users.LocalUser;
import net.paguo.search.controller.ComplaintSearchController;
import net.paguo.web.wicket.providers.SearchListProvider;
import net.paguo.web.wicket.providers.ComplaintListProvider;
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

import java.util.Arrays;


/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 0:17:11
 */
public class ComplaintsPage extends FailurePage<ClientComplaint> {

    @SpringBean
    ComplaintSearchController searchController;

    public void setSettingsController(ApplicationSettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public ComplaintSearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(ComplaintSearchController searchController) {
        this.searchController = searchController;
    }


    public ComplaintsPage(PageParameters parameters) {
        super(parameters);
    }

    protected SearchListProvider<ClientComplaint> getSearchProvider(String s) {
        return new SearchListProvider<ClientComplaint>(getSearchController(), s);
    }

    protected ComplaintListProvider getDefaultProvider() {
        return new ComplaintListProvider();
    }

    public ComplaintsPage() {
        super();
    }

    protected void initDefault(IDataProvider provider) {
        DataView items;
        int perPage = getPerPageSettings();

        String itemsId = "items";
        if (perPage > 0) {
            items = new ComplaintDataView
                    (itemsId, provider, perPage);
            items.setItemsPerPage(perPage);
        } else {
            items = new ComplaintDataView(itemsId, provider);
        }
        add(items);
        add(new PagingNavigator("navigator", items));
    }

    protected void initConstantCompoments() {
        add(HeaderContributor.forCss(ComplaintsPage.class, "wstyles.css"));
        add(new FeedbackPanel("feedback"));
        final Link create = new Link("create") {
            public void onClick() {
                setResponsePage(ComplaintCreatePage.class);
            }
        };
        add(create);
        add(new ComplaintsSearchForm("search"));
        add(new Link("reindex") {
            public void onClick() {
                getSearchController().reindex();
            }
        });

        secureElement(create, ClientComplaint.class, Arrays.asList(CREATE));
    }

    private class ComplaintDataView extends DataView {
        @SpringBean
        NetworkFailureController controller;

        public NetworkFailureController getController() {
            return controller;
        }

        public void setController(NetworkFailureController controller) {
            this.controller = controller;
        }

        ComplaintDataView(String id, IDataProvider dataProvider) {
            super(id, dataProvider);
        }

        public ComplaintDataView(String id, IDataProvider dataProvider, int itemsPerPage) {
            super(id, dataProvider, itemsPerPage);
        }

        protected void populateItem(Item item) {
            final ClientComplaint problem = (ClientComplaint) item.getModelObject();
            item.setModel(new CompoundPropertyModel(problem));
            item.add(new Label("id"));
            item.add(DateLabel.forDatePattern("failureTime", new Model(problem.getFailureTime()), "dd-MM-yy HH:mm"));
            String clientName = "";
            final ClientItem client = problem.getClient();
            if (client != null) {
                clientName = client.getClientName();
            } else if (!StringUtils.isEmpty(problem.getEnteredClient())) {
                clientName = problem.getEnteredClient();
            }
            item.add(new Label("clientName", new Model(clientName)));
            item.add(new Label("failureDescription"));
            item.add(new Label("restoreAction.failureCause"));
            item.add(new Label("restoreAction.restoreAction"));
            item.add(DateLabel.forDatePattern("restoreAction.restoreTime", "dd-MM-yy HH:mm"));
            final LocalUser created = problem.getUserCreated();
            String labelString = "-";
            if (created != null) {
                final PersonalData personalData = created.getPersonalData();
                if (personalData != null && !StringUtils.isEmpty(personalData.getFamilyName())) {
                    labelString = personalData.getFamilyName();
                } else {
                    labelString = created.getPermissionEntry().getUserName();
                }
            }
            item.add(new Label("userCreated", new Model(labelString)));
            final Link details = new Link("problemDetails") {
                public void onClick() {
                    final PageParameters pageParameters = new PageParameters();
                    pageParameters.add("problemId", String.valueOf(problem.getId()));
                    setResponsePage(ComplaintCreatePage.class, pageParameters);
                }
            };
            item.add(details);

            if (problem.getRestoreAction() != null && problem.getRestoreAction().getCompleted()){
                secureElement(details, ClientComplaint.class,
                        Arrays.asList(OVERRIDE));
            }else{
                secureElement(details, ClientComplaint.class,
                        Arrays.asList(CHANGE, OVERRIDE));
            }

            final Link child = new Link("problemDelete") {
                public void onClick() {
                    try {
                        getController().deleteComplaint(problem);
                        String message = getLocalizer().getString("complaint.deleted", ComplaintDataView.this);
                        Session.get().info(message);
                    } catch (ControllerException e) {
                        Session.get().error("Cannot delete problem. Check for child complaints");
                    }
                }
            };
            child.add(new SimpleAttributeModifier("onclick", "return confirm('Are you sure?');"));
            item.add(child);
            secureElement(child, ClientComplaint.class, Arrays.asList(DELETE));
        }
    }
}

