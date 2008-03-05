package net.paguo.web.wicket;

import org.apache.wicket.ajax.IAjaxCallDecorator;

/**
 * User: sreentenko
 * Date: 05.03.2008
 * Time: 0:37:57
 */
public class ProgressCallDecorator implements IAjaxCallDecorator {
    private String elementId;
    private static final String template = "document.getElementById('{0}').style.display = '{1}'; ";

    public ProgressCallDecorator(String elementId) {
        this.elementId = elementId;
    }

    public CharSequence decorateScript(CharSequence script) {
        return "document.getElementById('" + elementId + "').style.display='inline'; " + script;
    }

    public CharSequence decorateOnSuccessScript(CharSequence script) {
        return "document.getElementById('" + elementId + "').style.display='none'; " + script;
    }

    public CharSequence decorateOnFailureScript(CharSequence script) {
        return decorateOnSuccessScript(script);
    }
}
