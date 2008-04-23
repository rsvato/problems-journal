package net.paguo.web.wicket.components;

import net.paguo.controller.auxilliary.StreetsController;
import net.paguo.domain.auxilliary.StreetDictionary;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Reyentenko
 */
public class StreetAutoCompleteTextField extends AutoCompleteTextField {
    @SpringBean
    private StreetsController controller;

    public StreetAutoCompleteTextField(String id, IModel model) {
        super(id, model);
        add(new IValidator(){
            public void validate(IValidatable validatable) {
                final String value = (String) validatable.getValue();
                final List<StreetDictionary> dictionaries = getController().findByNameStrict(value);
                if (dictionaries == null || dictionaries.isEmpty()){
                    validatable.error(newError(value));
                }
            }

            private ValidationError newError(String value){
                ValidationError result = new ValidationError();
                result.addMessageKey("missingStreet");
                result.setVariable("street", value);
                return result;
            }
        });
    }

    protected Iterator getChoices(String input) {
        if (StringUtils.isEmpty(input)) {
            return Collections.EMPTY_LIST.iterator();
        }
        final List<StreetDictionary> list = getController().findByName(input);
        final List<String> flatList = new ArrayList<String>();
        for (StreetDictionary streetDictionary : list) {
            flatList.add(streetDictionary.getName());
        }
        return flatList.iterator();
    }

    public StreetsController getController() {
        return controller;
    }

    public void setController(StreetsController controller) {
        this.controller = controller;
    }

}
