package fr.ele.ui.search.mustache;

import java.beans.PropertyDescriptor;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;

import fr.ele.core.search.Search;
import fr.ele.core.search.StringOperator;

public class SearchToUi {
    public static <T extends Search> List<UiCriteria> transform(Class<T> clazz) {
        List<UiCriteria> criterias = new LinkedList<UiCriteria>();
        PropertyDescriptor[] properties = BeanUtils
                .getPropertyDescriptors(clazz);
        for (PropertyDescriptor property : properties) {
            String name = property.getName();
            if (!name.equals("class")) {
                UiCriteria criteria = new UiCriteria();
                criteria.setName(name);
                criteria.setTitle(WordUtils.capitalize(name));
                criteria.addOperators(StringOperator.values());
                criteria.setType(ValueType.STRING);
                criterias.add(criteria);
            }
        }
        return criterias;
    }
}
