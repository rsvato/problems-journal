package net.paguo.generic.dao;

/**
 * User: sreentenko
 * Date: 12.02.2008
 * Time: 1:47:35
 */
public class SortParameters {
    private String sortProperty;
    private boolean ascending;

    public SortParameters(String sortProperty, boolean ascending) {
        this.sortProperty = sortProperty;
        this.ascending = ascending;
    }

    public SortParameters(String sortProperty) {
        this(sortProperty, true);
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
