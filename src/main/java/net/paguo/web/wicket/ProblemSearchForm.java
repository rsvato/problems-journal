package net.paguo.web.wicket;

import org.apache.wicket.PageParameters;

/**
 * User: sreentenko
 * Date: 03.12.2007
 * Time: 0:45:40
 */
public class ProblemSearchForm extends ComplaintsSearchForm {
    public ProblemSearchForm(String id) {
        super(id);
    }

    @Override
    protected FailurePage findResponsePage(PageParameters parameters) {
        return new NetworkProblemsPage(parameters);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
