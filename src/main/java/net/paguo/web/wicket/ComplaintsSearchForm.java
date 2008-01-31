package net.paguo.web.wicket;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * User: sreentenko
 * Date: 29.11.2007
 * Time: 0:05:36
 */
public class ComplaintsSearchForm extends Form {
    private static final Log log = LogFactory.getLog(ComplaintsSearchForm.class);


    public ComplaintsSearchForm(String id) {
        super(id, new CompoundPropertyModel(new SearchCriteria()));
        add(new TextField("searchCriteria").setRequired(true));

    }


    @Override
    protected final void onSubmit() {

        final SearchCriteria criteria = (SearchCriteria) getModelObject();
        redirect(criteria);

    }

    private void redirect(SearchCriteria criteria) {
        PageParameters parameters = new PageParameters();
        URLCodec codec = new URLCodec("UTF-8");

        try {
            parameters.put("search", codec.encode(criteria.getSearchCriteria()));
        } catch (EncoderException e) {
            log.error(e);
        }
        setResponsePage(findResponsePage(parameters));
    }

    protected FailurePage findResponsePage(PageParameters parameters) {
        return new ComplaintsPage(parameters);
    }
}
