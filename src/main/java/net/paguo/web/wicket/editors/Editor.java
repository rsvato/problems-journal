package net.paguo.web.wicket.editors;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;


/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 2:06:15
 */
public abstract class Editor extends Fragment {
    private static final long serialVersionUID = -1853512733187406294L;
    protected static final String EDIT = "edit";

    public Editor(String id, String markupId, MarkupContainer markupProvider) {
        super(id, markupId, markupProvider);
    }
}
