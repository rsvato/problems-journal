package net.paguo.web.wicket.components;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.behavior.SimpleAttributeModifier;

/**
 * @author Reyentenko
 */
public abstract class ConfirmationLink extends Link {
    private static final long serialVersionUID = 2501421504815021734L;

    public ConfirmationLink(String id){
        super(id);
        add(new SimpleAttributeModifier("onclick", "return confirm('Are you sure?');"));
    }
}
