package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.domain.application.ApplicationSettings;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 02.02.2008
 * Time: 1:39:49
 */
public class SettingsAwarePage extends SecuredWebPage {
    @SpringBean
    ApplicationSettingsController settingsController;
    private static final String ITEMS_PER_PAGE_KEY = "failureController.itemsPerPage";
    private static final String MAX_TESTING_COUNT = "requestController.maxTestingCount";
    private static final long serialVersionUID = 4105583608381816997L;

    public ApplicationSettingsController getSettingsController() {
        return settingsController;
    }

    protected int getPerPageSettings() {
        int perPage = 0;
        ApplicationSettings itemsPerPageSettings = getSettingsController().findByKey(ITEMS_PER_PAGE_KEY);
        if (itemsPerPageSettings != null
                && !StringUtils.isEmpty(itemsPerPageSettings.getValue())
                && StringUtils.isNumeric(itemsPerPageSettings.getValue())){
            perPage = Integer.decode(itemsPerPageSettings.getValue());
        }
        return perPage;
    }

    protected int getMaxTestingCount(){
        int maxRetries = 3;
        ApplicationSettings itemsPerPageSettings = getSettingsController().findByKey(MAX_TESTING_COUNT);
        if (itemsPerPageSettings != null
                && !StringUtils.isEmpty(itemsPerPageSettings.getValue())
                && StringUtils.isNumeric(itemsPerPageSettings.getValue())){
            maxRetries = Integer.decode(itemsPerPageSettings.getValue());
        }
        return maxRetries;
    }
}
