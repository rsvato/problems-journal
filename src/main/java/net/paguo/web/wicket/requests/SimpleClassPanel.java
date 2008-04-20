package net.paguo.web.wicket.requests;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.hibernate.validator.NotNull;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:44:44
 */
public class SimpleClassPanel extends Panel {

    public SimpleClassPanel(String id, Serializable object) {
        super(id, new CompoundPropertyModel(object));
        RepeatingView fields = new RepeatingView("fields");
        for (Field field : object.getClass().getDeclaredFields()) {
            WebMarkupContainer row = new WebMarkupContainer(fields.newChildId());
            fields.add(row);

            IModel labelModel = new StringResourceModel(field.getName(), this, null);
            // Use Wicket's i18n to name the field.
            row.add(new Label("name", labelModel));

            final Class<?> type = field.getType();

            // Create the editor.
            IModel model = new PropertyModel(object, field.getName());
            Component editor = null;
            if (String.class.isAssignableFrom(type)) {
                // Check for @Required on the field.
                boolean required = field.getAnnotation(NotNull.class) != null;
                editor = new StringEditor("editor", model, labelModel, required, row);
            }
            row.add(editor);
        }
    }

    private static class StringEditor extends Editor {
        public StringEditor(String id, IModel model, IModel labelModel, boolean required,
                            MarkupContainer container) {
            super(id, "stringEditor", container);
            add(new TextField("edit", model).setLabel(labelModel).setRequired(required));
        }
    }
}
