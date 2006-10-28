package net.paguo.web;

import org.apache.struts.tiles.beans.MenuItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ResourceBundle;
import java.util.Locale;
import java.text.MessageFormat;

/**
 * User: slava
 * Date: 27.10.2006
 * Time: 23:50:50
 * Version: $Id$
 */
public class Menu implements MenuItem {
    private String value;
    private String link;
    private String icon;
    private String tooltip;
    public static final Locale DEFAULT_LOCALE = new Locale("ru", "", "");
    public static final Log log = LogFactory.getLog(Menu.class);

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getValue() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", DEFAULT_LOCALE);
        if (bundle != null){
            try {
                final Object result = bundle.getObject(value);
                if (result != null){
                    return String.valueOf(result);
                }
            } catch (Exception e) {
                log.error("Exception while retrieving value for key " + value + ": " + e);
            }
        }else{
            log.debug("Cannot find resource bundle");
        }
        return MessageFormat.format("????{0}????", value);
    }

    public String getKey(){
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
