package net.paguo.web.wicket.requests;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;


/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 2:06:15
 */
public abstract class Editor extends Fragment {
    public Editor(String id, String markupId, MarkupContainer markupProvider) {
        super(id, markupId, markupProvider);
    }
}
