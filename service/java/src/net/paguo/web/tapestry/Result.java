package net.paguo.web.tapestry;

import org.apache.tapestry.html.BasePage;

/**
 * User: slava
 * Date: 21.10.2006
 * Time: 0:21:59
 * Version: $Id$
 */
public abstract class Result extends BasePage {
    public abstract void setStockValue(Integer stockValue);
    public abstract Integer getStockValue();
}
