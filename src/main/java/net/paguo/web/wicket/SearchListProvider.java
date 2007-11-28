package net.paguo.web.wicket;

import net.paguo.search.controller.SearchController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * User: sreentenko
 * Date: 28.11.2007
 * Time: 23:55:50
 */
public class SearchListProvider<T extends Serializable> implements IDataProvider {

    private static final Log log = LogFactory.getLog(SearchListProvider.class);

    private List<T> results;
    private SearchController<T> controller;

    public SearchController<T> getController() {
        return controller;
    }

    public void setController(SearchController<T> controller) {
        this.controller = controller;
    }

    public SearchListProvider() {
    }

    public SearchListProvider(SearchController<T> controller, String criteria){
       log.debug("Creating with controller");
       this.controller = controller;
        try{
            this.results = controller.search(criteria);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public SearchListProvider(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        if (this.results == null && controller != null){
           this.results =
        }
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Iterator iterator(int first, int count) {
        if (first > size() || first + count > size()){
            return Collections.EMPTY_LIST.iterator();
        }
        return results.subList(first, first + count).iterator();
    }

    public int size() {
        return results.size();
    }

    @SuppressWarnings("unchecked")
    public IModel model(Object object) {
        return new Model((T) object);
    }

    public void detach() {
        results = null;
    }
}
