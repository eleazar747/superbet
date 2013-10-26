package fr.ele.ui.search;

import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;

import fr.ele.core.search.NumberOperator;
import fr.ele.core.search.NumberValueCriteria;
import fr.ele.core.search.Search;
import fr.ele.core.search.SearchOperator;
import fr.ele.core.search.StringOperator;
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
            FormField field = null;
            if (!name.equals("class")) {
                if (StringValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    field = createCriteriaField(name, FieldType.STRING,
                            StringOperator.values());

                } else if (NumberValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    field = createCriteriaField(name, FieldType.NUMBER,
                            NumberOperator.values());
                }
                schema.add(name, field);
            }
        }
        return form;
    }

    private static FormField createCriteriaField(String name,
            FieldType valueType, SearchOperator... operators) {
        FormField field = new FormField();
        field.setTitle(WordUtils.capitalize(name));
        field.setType(FieldType.OBJECT);
        FormField operatorField = new FormField();
        operatorField.setType(FieldType.STRING);
        for (SearchOperator operator : operators) {
            operatorField.addValue(operator.name());
        }

        FormField valueField = new FormField();
        valueField.setType(valueType);

        Map<String, FormField> subs = new LinkedHashMap<String, FormField>();
        subs.put("operator", operatorField);
        subs.put("value", valueField);
        field.setProperties(subs);
        return field;
    }

}
