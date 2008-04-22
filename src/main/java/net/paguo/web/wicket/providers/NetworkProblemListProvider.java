package net.paguo.web.wicket.providers;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;
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
        IntRange range = new IntRange(first, first + count);
        log.debug("Created range " + range);
        return getController().getProblems(range).iterator();
    }

    public int size() {
        return getController().getProblemsCount();
    }

    public IModel model(Object o) {
        return new Model((NetworkProblem) o);
    }

    public void detach() {
    }
}
