package net.paguo.web.wicket.requests;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.commons.lang.StringUtils;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.ClientInformation;
import net.paguo.domain.application.ApplicationSettings;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.common.PersonalData;
import net.paguo.web.wicket.providers.RequestListProvider;
import net.paguo.controller.ApplicationSettingsController;

/**
 * @author Reyentenko
 */
public class RequestListPanel extends Panel {
    private static final long serialVersionUID = -1000606936306698884L;

    @SpringBean
    ApplicationSettingsController settingsController;

    public ApplicationSettingsController getSettingsController() {
        return settingsController;
    }

    private static final String ITEMS_PER_PAGE_KEY = "failureController.itemsPerPage";

    protected int getPerPageSettings() {
        int perPage = 0;
        ApplicationSettings itemsPerPageSettings = getSettingsController().findByKey(ITEMS_PER_PAGE_KEY);
        if (itemsPerPageSettings != null
                && !StringUtils.isEmpty(itemsPerPageSettings.getValue())
                && StringUtils.isNumeric(itemsPerPageSettings.getValue())) {
            perPage = Integer.decode(itemsPerPageSettings.getValue());
        }
        return perPage;
    }

    public RequestListPanel(String id, ProcessStage stage) {
        super(id);
        RequestListProvider provider = new RequestListProvider();
        provider.setStage(stage);
        final DataView view = new DataView("table", provider, getPerPageSettings()) {
            private static final long serialVersionUID = 2132859859365329494L;

            protected void populateItem(Item item) {
                Request request = (Request) item.getModelObject();
                item.add(new Label("id", request.getId().toString()));
                item.add(DateLabel.forDatePattern("creationDate",
                        new Model(request.getCreationDate()), "dd-MM-yy"));
                final LocalUser created = request.getAuthor();
                String labelString = "-";
                if (created != null) {
                    final PersonalData personalData = created.getPersonalData();
                    if (personalData != null && !StringUtils.isEmpty(personalData.getFamilyName())) {
                        labelString = personalData.getFamilyName();
                    } else {
                        labelString = created.getPermissionEntry().getUserName();
                    }
                }
                item.add(new Label("author", new Model(labelString)));
                final ClientInformation clientInformation = request.getClientInformation();
                item.add(new Label("clientDesignation", clientInformation.getClientDesignation()));
                item.add(new Label("clientType",
                        new StringResourceModel(clientInformation.getClientType().name().toLowerCase(),
                        this, null)));
                item.add(new Label("stage",
                        new StringResourceModel(request.getCurrentStage().name().toLowerCase(), this, null)));
                item.add(new Label("address", String.valueOf(clientInformation.getAddress())));
                item.add(new Label("contactInfo", String.valueOf(clientInformation.getContact())));
            }
        };

        add(new OrderByBorder("orderByDate", "creationDate", provider) {
            private static final long serialVersionUID = 7506509423384662455L;

            protected void onSortChanged() {
                view.setCurrentPage(0);
            }
        });
        add(new PagingNavigator("navigator", view));
        add(new NavigatorLabel("navlabel", view));
        add(view);
    }
}
