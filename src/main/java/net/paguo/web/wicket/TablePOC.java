package net.paguo.web.wicket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * User: sreentenko
 * Date: 22.01.2008
 * Time: 0:02:54
 */
public class TablePOC extends WebPage {
    final DataView view;

    private static final Log log = LogFactory.getLog(TablePOC.class);

    public TablePOC() {
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        final WebMarkupContainer tbl = new WebMarkupContainer("tbl");
        tbl.setOutputMarkupId(true);
        view = new DataView("table", new TablePOCProvider(new SearchCriteria()), 20) {
            protected void populateItem(Item item) {
                TableItem tableItem = (TableItem) item.getModelObject();
                item.add(new Label("id", String.valueOf(tableItem.getId())));
                item.add(new Label("description", String.valueOf(tableItem.description)));
            }
        };

        view.setOutputMarkupId(true);
        tbl.add(view);
        final SearchForm searchForm = new SearchForm("search", new SearchCriteria());
        searchForm.add(new AjaxButton("submitSearch", searchForm) {
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                search((SearchCriteria) form.getModelObject());
                final int resultCount = view.getDataProvider().size();
                if (resultCount > 0){
                    tbl.add(new AttributeModifier("style", true, new Model("display:block;")));
                    if (resultCount > 3000){
                       Session.get().warn("Too many results. Try to refine search");
                    }
                }else{
                    tbl.add(new AttributeModifier("style", true, new Model("display:none;")));
                    Session.get().info("No results");
                }
                target.addComponent(tbl);
                target.addComponent(feedbackPanel);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form form) {
                target.addComponent(form);
            }
        });
        add(searchForm);
        final AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", view){
            @Override
            protected void onAjaxEvent(AjaxRequestTarget target) {
                target.addComponent(tbl);
            }
        };
        tbl.add(navigator);
        add(tbl);

    }

    public void search(SearchCriteria criteria){
        log.debug(criteria);
        view.setCurrentPage(0);
        ((TablePOCProvider) view.getDataProvider()).setCriteria(criteria);

    }

    private class TableItem implements Serializable {
        private int id;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    private class TablePOCProvider implements IDataProvider {
        private SearchCriteria criteria;

        public TablePOCProvider(SearchCriteria searchCriteria) {
            this.criteria = searchCriteria;
        }

        public SearchCriteria getCriteria() {
            return criteria;
        }

        public void setCriteria(SearchCriteria criteria) {
            this.criteria = criteria;
        }

        public Iterator iterator(int first, int count) {
            if (criteria != null && criteria.getSearchField() != null) {
                List<TableItem> result = new ArrayList<TableItem>();
                for (int i = first; i < first + count; i++) {
                    TableItem item = new TableItem();
                    item.setId(i);
                    item.setDescription("item #" + String.valueOf(i));
                    result.add(item);
                }
                return result.iterator();
            }
            return Collections.EMPTY_LIST.iterator();
        }

        public int size() {
            if (criteria != null && criteria.getSearchField() != null){
                return criteria.getSearchField().equals("FOO") ? 4000 : 200;
            }
            return 0;
        }

        public IModel model(Object object) {
            return new Model((TableItem) object);
        }

        public void detach() {
        }
    }

    private class SearchCriteria implements Serializable {
        String searchField;

        public String getSearchField() {
            return searchField;
        }

        public void setSearchField(String searchField) {
            this.searchField = searchField;
        }
    }

    private class SearchForm extends Form {
        public SearchForm(String s, SearchCriteria criteria) {
            super(s, new CompoundPropertyModel(criteria));
            add(new TextField("searchField"));

        }
    }
}
