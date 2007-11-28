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
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class ComplaintsPage extends SecuredWebPage{
    private static final String ITEMS_PER_PAGE_KEY = "failureController.itemsPerPage";
    private static final Log log = LogFactory.getLog(ComplaintsPage.class);

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


    public ComplaintsPage(PageParameters parameters){
        final String parameter = parameters.getString("search");
        IDataProvider provider = new ComplaintListProvider();
        if (parameters.containsKey("search") || !StringUtils.isEmpty(parameter)){
            try{
              URLCodec codec = new URLCodec("UTF-8");
                final String s = codec.decode(parameter);
                provider = new SearchListProvider<ClientComplaint>(getSearchController(), s);
            }catch (DecoderException e){
                log.error(e);
                Session.get().warn("Error decoding search string");
            }
        }
        initDefault(provider);
        initConstantCompoments();
    }

    public ComplaintsPage() {
        initDefault(new ComplaintListProvider());
        initConstantCompoments();
    }

    private void initDefault(IDataProvider provider) {
        ComplaintDataView items;
        int perPage = getPerPageSettings();

        if (perPage > 0) {
            items = new ComplaintDataView
                    ("items", provider, perPage);
            items.setItemsPerPage(perPage);
        } else {
            items = new ComplaintDataView("items", provider);
        }
        add(items);
        add(new PagingNavigator("navigator", items));
    }

    private int getPerPageSettings() {
        int perPage = 0;
        ApplicationSettings itemsPerPageSettings = getSettingsController().findByKey(ITEMS_PER_PAGE_KEY);
        if (itemsPerPageSettings != null
                && !StringUtils.isEmpty(itemsPerPageSettings.getValue())
                && StringUtils.isNumeric(itemsPerPageSettings.getValue())){
            perPage = Integer.decode(itemsPerPageSettings.getValue());
        }
        return perPage;
    }

    private void initConstantCompoments() {
        add(HeaderContributor.forCss(ComplaintsPage.class, "wstyles.css"));
        add(new FeedbackPanel("feedback"));
        add(new Link("create"){
            public void onClick() {
                setResponsePage(ComplaintCreatePage.class);
            }
        });
        add(new ComplaintsSearchForm("search"));
        add(new Link("reindex"){
            public void onClick() {
                getSearchController().reindex();
            }
        });
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