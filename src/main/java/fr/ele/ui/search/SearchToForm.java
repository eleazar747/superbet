package fr.ele.ui.search;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;

import fr.ele.core.search.NumberValueCriteria;
import fr.ele.core.search.Search;
import fr.ele.core.search.StringValueCriteria;

public class SearchToForm {

    public <T extends Search> Form tranform(Class<T> searchClass) {
        Form form = new Form();
        FormNode schema = new FormNode();
        form.setSchema(schema);

        PropertyDescriptor[] properties = BeanUtils
                .getPropertyDescriptors(searchClass);
        for (PropertyDescriptor property : properties) {
            String name = property.getName();
            if (!name.equals("class")) {
                FormField field = new FormField();
                field.setTitle(WordUtils.capitalize(name));
                if (StringValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    FormField operatorField = new FormField();
                    operatorField.setType(FieldType.STRING);

                    Map<String, FormField> subs = new HashMap<String, FormField>();
                    subs.put("operator", operatorField);
                    field.setType(FieldType.STRING);
                } else if (NumberValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    field.setType(FieldType.NUMBER);
                }
                schema.add(name, field);
            }
        }
        return form;
    }
}
