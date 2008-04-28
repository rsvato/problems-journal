package net.paguo.web.wicket.providers;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;
import org.apache.commons.lang.math.IntRange;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * @author Reyentenko
 */
public class RequestListProvider extends SortableDataProvider {
    private static final long serialVersionUID = -2693861579254605923L;
    private ProcessStage stage;
    @SpringBean
    private RequestController controller;

    public RequestListProvider() {
        InjectorHolder.getInjector().inject(this);
        setSort("creationDate", false);
    }

    public Iterator iterator(int first, int count) {
        IntRange range = new IntRange(first, first + count);
        final SortParam sort = getSort();
        return getController().findByStatus(stage, sort.getProperty(),
                sort.isAscending(), range).iterator();
    }

    public int size() {
        return getController().count(stage);
    }

    public IModel model(Object object) {
        return new Model((Request) object);
    }

    public void detach() {
    }


    public RequestController getController() {
        return controller;
    }

    public void setController(RequestController controller) {
        this.controller = controller;
    }

    public ProcessStage getStage() {
        return stage;
    }

    public void setStage(ProcessStage stage) {
        this.stage = stage;
    }
}
