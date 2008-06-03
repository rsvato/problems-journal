package net.paguo.web.wicket.requests;

import net.paguo.visual.EditorEnum;
import net.paguo.visual.InterfaceField;
import net.paguo.web.wicket.components.StreetAutoCompleteTextField;
import net.paguo.web.wicket.editors.Editor;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.*;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        final List<Field> declaredFields = Arrays.asList(object.getClass().getDeclaredFields());

        Collections.sort(declaredFields, new Comparator<Field>() {
            public int compare(Field o1, Field o2) {
                InterfaceField desc = o1.getAnnotation(InterfaceField.class);
                InterfaceField otherDesc = o2.getAnnotation(InterfaceField.class);
                if (desc != null && otherDesc != null) {
                    return Integer.valueOf(desc.order()).
                            compareTo(otherDesc.order());
                }
                return 0;
            }
        });

        for (Field field : declaredFields) {
            final InterfaceField fieldDescription = field.getAnnotation(InterfaceField.class);
            boolean create = field.getAnnotation(Id.class) == null
                    && fieldDescription != null;
            if (create) {
                WebMarkupContainer row = new WebMarkupContainer(fields.newChildId());
                IModel model = new PropertyModel(object, field.getName());
                Component editor;

                IModel labelModel = new StringResourceModel(field.getName(), this, null);
                boolean required = field.getAnnotation(NotNull.class) != null;

                final EditorEnum editorEnum = fieldDescription.editor();
                int length = fieldDescription.length();
                if (length == 0){
                    final Length annotation = field.getAnnotation(Length.class);
                    if (annotation != null){
                        length = annotation.max();
                    }
                }
                switch (editorEnum) {
                    case STRING:
                        editor = new StringEditor("editor", model, labelModel, required, row, length);
                        break;
                    case DATE:
                        editor = new DateEditor("editor", model, labelModel, required, row);
                        break;
                    case DATETIME:
                        editor = new DatetimeEditor("editor", model, labelModel, required, row);
                        break;
                    case LONGTEXT:
                        editor = new LongtextEditor("editor", model, labelModel, required, row);
                        break;
                    case ENUM:
                        editor = new EnumEditor("editor", model, labelModel, required, row, (Class<Enum>) field.getType());
                        break;
                    case STREET:
                        editor = new StreetEditor("editor", model, labelModel, required, row);
                        break;
                    case LOCALUSER:
                    default:
                        throw new IllegalArgumentException("Not implemented yet: " + editorEnum);
                }

                if (editor != null) {
                    row.add(editor);
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
                            MarkupContainer container, final int lentgh) {
            super(id, "stringEditor", container);
            add(new TextField(EDIT, model) {
                @Override
                protected void onComponentTag(ComponentTag tag) {
                    super.onComponentTag(tag);
                    if (lentgh > 0) {
                        tag.put("maxlength", lentgh);
                    }
                }
            }.setLabel(labelModel).setRequired(required));
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

    private static class StreetEditor extends Editor {
        private static final long serialVersionUID = -458236221242803913L;

        public StreetEditor(String id, IModel model, IModel labelModel, boolean required,
                            MarkupContainer container) {
            super(id, "streetEditor", container);
            add(new StreetAutoCompleteTextField(EDIT, model)
                    .setLabel(labelModel).setRequired(required));
        }
    }

    private class EnumEditor<T> extends Editor {
        private static final long serialVersionUID = 1415079006228084713L;

        public EnumEditor(String id, IModel model, IModel labelModel, boolean required, WebMarkupContainer row, final Class<Enum> enumKlass) {
            super(id, "enumEditor", row);
            IModel enumChoices = new AbstractReadOnlyModel() {
                private static final long serialVersionUID = 2290774896713500536L;

                @Override
                public Object getObject() {
                    return Arrays.asList(enumKlass.getEnumConstants());
                }

            };

            DropDownChoice ddc = new DropDownChoice("edit", model, enumChoices, new IChoiceRenderer() {
                private static final long serialVersionUID = -7479554544309772575L;

                public Object getDisplayValue(Object object) {
                    Enum o = (Enum) object;
                    StringResourceModel m = new StringResourceModel(o.name().toLowerCase(),
                            SimpleClassPanel.this, null);
                    return m.getString();
                }

                public String getIdValue(Object object, int index) {
                    Enum o = (Enum) object;
                    return String.valueOf(o.ordinal());
                }
            });
            ddc.setLabel(labelModel).setRequired(required);
            add(ddc);
        }
    }
}
