package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.application.ApplicationSettings;
import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.common.PersonalData;
import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.users.LocalUser;
import net.paguo.search.controller.ComplaintSearchController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.List;


/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 0:17:11
 */
public class ComplaintsPage extends SecuredWebPage{
    private static final String ITEMS_PER_PAGE_KEY = "failureController.itemsPerPage";

    @SpringBean
    NetworkFailureController controller;

    @SpringBean
    ApplicationSettingsController settingsController;

    @SpringBean
    ComplaintSearchController searchController;

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

    public ComplaintSearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(ComplaintSearchController searchController) {
        this.searchController = searchController;
    }

    public ComplaintsPage() {
        final ComplaintListProvider provider = new ComplaintListProvider();
        ApplicationSettings itemsPerPageSettings = getSettingsController().findByKey(ITEMS_PER_PAGE_KEY);
        ComplaintDataView items;
        if (itemsPerPageSettings != null
                && !StringUtils.isEmpty(itemsPerPageSettings.getValue())
                && StringUtils.isNumeric(itemsPerPageSettings.getValue())){
            final Integer perPage = Integer.decode(itemsPerPageSettings.getValue());
            items = new ComplaintDataView
                    ("items", provider, perPage);
            items.setItemsPerPage(perPage);
        }else{
            items = new ComplaintDataView("items", provider);
        }
        add(HeaderContributor.forCss(ComplaintsPage.class, "wstyles.css"));
        add(items);
        add(new PagingNavigator("navigator", items));
        add(new FeedbackPanel("feedback"));
        //TODO: change target
        add(new Link("create"){
            public void onClick() {
                setResponsePage(ComplaintCreatePage.class);
            }
        });
        add(new SearchForm("search", getSearchController(), new SearchCriteria()));
        add(new Link("reindex"){
            public void onClick() {
                getSearchController().reindex();
            }
        });
    }
}

class SearchCriteria implements Serializable {
    private String searchCriteria;

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}

final class SearchForm extends Form{
    private ComplaintSearchController searchController;
    private static final Log log = LogFactory.getLog(SearchForm.class);

    public ComplaintSearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(ComplaintSearchController searchController) {
        this.searchController = searchController;
    }

    public SearchForm(String id, ComplaintSearchController search, SearchCriteria criteria) {
        super(id, new CompoundPropertyModel(criteria));
        this.searchController = search;
        add(new TextField("searchCriteria").setRequired(true));
    }



    @Override
    protected void onSubmit() {
        try {
            final SearchCriteria criteria = (SearchCriteria) getModelObject();
            log.debug("Entered criteria: " + criteria.getSearchCriteria());
            final List<ClientComplaint> list = searchController.search(criteria.getSearchCriteria());
            log.debug("Find " + list.size() + " results");
            setResponsePage(ComplaintsPage.class);
        } catch (ParseException e) {
            log.error(e);
        }
    }
}

class ComplaintDataView extends DataView {
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
        item.add(DateLabel.forDatePattern("failureTime", new Model(problem.getFailureTime()), "dd-MM-yy hh:mm"));
        String clientName = "";
        final ClientItem client = problem.getClient();
        if (client != null){
            clientName = client.getClientName();
        }else if (!StringUtils.isEmpty(problem.getEnteredClient())){
            clientName = problem.getEnteredClient();
        }
        item.add(new Label("clientName", new Model(clientName)));
        item.add(new Label("failureDescription"));
        item.add(new Label("restoreAction.failureCause"));
        item.add(new Label("restoreAction.restoreAction"));
        item.add(DateLabel.forDatePattern("restoreAction.restoreTime", "dd-MM-yy hh:mm"));
        final LocalUser created = problem.getUserCreated();
        String labelString = "-";
        if (created != null){
            final PersonalData personalData = created.getPersonalData();
            if (personalData != null && ! StringUtils.isEmpty(personalData.getFamilyName())){
                labelString = personalData.getFamilyName();
            }else{
                labelString = created.getPermissionEntry().getUserName();
            }
        }
        item.add(new Label("userCreated", new Model(labelString)));
        //TODO: change target
        item.add(new Link("problemDetails"){
            public void onClick() {
                final PageParameters pageParameters = new PageParameters();
                pageParameters.add("problemId", String.valueOf(problem.getId()));
                setResponsePage(ComplaintCreatePage.class, pageParameters);
            }
        });
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
    }
}