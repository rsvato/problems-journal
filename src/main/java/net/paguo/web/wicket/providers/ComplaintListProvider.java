package net.paguo.web.wicket.providers;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.ClientComplaint;
import org.apache.commons.lang.math.IntRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * User: sreentenko
 * Date: 04.06.2007
 * Time: 1:06:28
 */
public class ComplaintListProvider implements IDataProvider {
    @SpringBean
    private NetworkFailureController controller;
    private static final Log log = LogFactory.getLog(ComplaintListProvider.class);

    public ComplaintListProvider() {
        InjectorHolder.getInjector().inject(this);
    }

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public Iterator iterator(int first, int count) {
        log.debug("Getting " + count + " values " + " from " + first);
        IntRange range = new IntRange(first, first + count);
        log.debug("Created range " + range);
        return getController().getClientComplaints(range).iterator();
    }

    public int size() {
        return getController().getComplaintsCount();
    }

    public IModel model(Object o) {
        return new Model((ClientComplaint) o);
    }

    public void detach() {
    }
}