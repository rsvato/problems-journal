package net.paguo.web.wicket.requests;

import net.paguo.visual.EditorEnum;
import net.paguo.visual.InterfaceField;
import net.paguo.web.wicket.editors.Editor;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.hibernate.validator.NotNull;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:44:44
 */
public class SimpleClassPanel extends Panel {
    private static final long serialVersionUID = 349278828425625244L;

    public SimpleClassPanel(String id, Serializable object) {
        super(id, new CompoundPropertyModel(object));
        RepeatingView fields = new RepeatingView("fields");
        for (Field field : object.getClass().getDeclaredFields()) {
            final InterfaceField fieldDescription = field.getAnnotation(InterfaceField.class);
            boolean create = field.getAnnotation(Id.class) == null
                    && fieldDescription != null;
            if (create) {
                WebMarkupContainer row = new WebMarkupContainer(fields.newChildId());
                IModel model = new PropertyModel(object, field.getName());
                Component editor = null;

                IModel labelModel = new StringResourceModel(field.getName(), this, null);
                boolean required = field.getAnnotation(NotNull.class) != null;

                final EditorEnum editorEnum = fieldDescription.editor();
                switch(editorEnum){
                    case STRING:
                        editor = new StringEditor("editor", model, labelModel, required, row);
                        break;
                    case DATE:
                        editor = new DateEditor("editor", model, labelModel, required, row);
                    case DATETIME:
                        editor = new DatetimeEditor("editor", model, labelModel, required, row);
                    case LONGTEXT:
                        editor = new LongtextEditor("editor", model, labelModel, required, row);
                    default:
                        throw new IllegalArgumentException("Not implemented yet");
                }

                if (editor != null) {
                    row.add(editor);
                    // Use Wicket's i18n to name the field.
                    row.add(new Label("name", labelModel));
                    fields.add(row);
                }
            }
        }
        add(fields);
    }

    private static class StringEditor extends Editor {
        private static final long serialVersionUID = 2258161700743436468L;

        public StringEditor(String id, IModel model, IModel labelModel, boolean required,
                            MarkupContainer container) {
            super(id, "stringEditor", container);
            add(new TextField(EDIT, model).setLabel(labelModel).setRequired(required));
        }
    }


    private static class DateEditor extends Editor {
        private static final long serialVersionUID = -5231036062874028859L;

        public DateEditor(String id, IModel model, IModel labelModel, boolean required,
                            MarkupContainer container) {
            super(id, "dateEditor", container);
            add(new DateField(EDIT, model).setLabel(labelModel).setRequired(required));
        }
    }

    private static class DatetimeEditor extends Editor {
        private static final long serialVersionUID = 5419926906291245030L;

        public DatetimeEditor(String id, IModel model, IModel labelModel, boolean required,
                            MarkupContainer container) {
            super(id, "datetimeEditor", container);
            add(new DateTimeField(EDIT, model).setLabel(labelModel).setRequired(required));
        }
    }

    private static class LongtextEditor extends Editor {
        private static final long serialVersionUID = -7922996235055367153L;

        public LongtextEditor(String id, IModel model, IModel labelModel, boolean required,
                            MarkupContainer container) {
            super(id, "longtextEditor", container);
            add(new TextArea(EDIT, model).setLabel(labelModel).setRequired(required));
        }
    }

}
