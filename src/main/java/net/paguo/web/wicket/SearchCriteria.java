package net.paguo.web.wicket;

import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 29.11.2007
 * Time: 0:04:46
 */
class SearchCriteria implements Serializable {
    private String searchCriteria;

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
