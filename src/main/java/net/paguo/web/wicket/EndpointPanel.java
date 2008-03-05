package net.paguo.web.wicket;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.NumberValidator;
import net.paguo.domain.equipment.ClientEndpoint;

/**
 * @author Reyentenko
 */
public class EndpointPanel extends Panel {
    private static final long serialVersionUID = 5998667002219914811L;

    public EndpointPanel(String id, IModel model) {
        super(id, model);
        add(new TextField("designation").setRequired(true));
        add(new TextField("vlanNumber").
                add(new NumberValidator.MinimumValidator(1l)).
                setRequired(true));
    }
}
