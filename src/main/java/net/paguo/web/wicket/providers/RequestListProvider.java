package net.paguo.web.wicket.providers;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.commons.lang.math.IntRange;

import java.util.Iterator;

import net.paguo.controller.request.RequestController;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;

/**
 * @author Reyentenko
 */
public class RequestListProvider extends SortableDataProvider {
    private static final long serialVersionUID = -2693861579254605923L;
    private ProcessStage stage;
    @SpringBean
    private RequestController controller;

    public RequestListProvider(){
        InjectorHolder.getInjector().inject(this);
        setSort("creationDate", false);
    }

    public Iterator iterator(int first, int count) {
        IntRange range = new IntRange(first, first + count);
        return getController().findByStatus(stage, getSort().getProperty(), getSort().isAscending(), range)
        return null;
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
