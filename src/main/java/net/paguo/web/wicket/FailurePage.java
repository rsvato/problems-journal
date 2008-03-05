package net.paguo.web.wicket;

import net.paguo.controller.ApplicationSettingsController;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.UsersController;
import net.paguo.domain.application.ApplicationSettings;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 03.12.2007
 * Time: 0:26:56
 */
public abstract class FailurePage<T extends Serializable> extends SettingsAwarePage {
    private static final Log log = LogFactory.getLog(FailurePage.class);

    public FailurePage(PageParameters parameters){
        IDataProvider provider = findProvider(parameters);
        initDefault(provider);
        initConstantCompoments();
    }

    public FailurePage(){
        initDefault(getDefaultProvider());
        initConstantCompoments();
    }

    @SpringBean
    NetworkFailureController controller;

    @SpringBean
    private UsersController usersController;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }

    protected abstract void initConstantCompoments();
    protected abstract void initDefault(IDataProvider provider);

    protected IDataProvider findProvider(PageParameters parameters) {
        final String parameter = parameters.getString("search");
        IDataProvider provider = getDefaultProvider();
        if (parameters.containsKey("search") || !StringUtils.isEmpty(parameter)) {
            try {
                URLCodec codec = new URLCodec("UTF-8");
                final String s = codec.decode(parameter);
                provider = getSearchProvider(s);
            } catch (DecoderException e) {
                log.error(e);
                Session.get().warn("Error decoding search string");
            }
        }
        return provider;
    }

    protected abstract SearchListProvider<T> getSearchProvider(String s);

    protected abstract IDataProvider getDefaultProvider();
}
