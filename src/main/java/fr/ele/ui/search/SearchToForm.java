package fr.ele.ui.search;

import java.beans.PropertyDescriptor;

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
                FieldType type = null;
                if (StringValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    type = FieldType.STRING;
                } else if (NumberValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    type = FieldType.NUMBER;
                }
                FormField field = new FormField();
                field.setTitle(WordUtils.capitalize(name));
                field.setType(type);
                schema.add(name, field);
            }
        }
        return form;
    }
}
