package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.injection.web.InjectorHolder;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * User: sreentenko
 * Date: 04.06.2007
 * Time: 1:06:28
 */
public class NetworkProblemListProvider implements IDataProvider {
    @SpringBean
    private NetworkFailureController controller;
    private static final Log log = LogFactory.getLog(NetworkProblemListProvider.class);

    public NetworkProblemListProvider() {
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
        return getController().getProblems(first, count, false).iterator();
    }

    public int size() {
        return getController().getProblemsCount();
    }

    public IModel model(Object o) {
        return new Model((NetworkProblem) o);
    }
}
