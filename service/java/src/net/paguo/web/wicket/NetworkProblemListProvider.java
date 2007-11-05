package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.injection.web.InjectorHolder;
import wicket.model.IModel;
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
        return getController().getProblems(first, count).iterator();
    }

    public int size() {
        return getController().getProblemsCount();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IModel model(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
