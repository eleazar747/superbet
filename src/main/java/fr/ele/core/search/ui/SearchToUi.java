package fr.ele.core.search.ui;

import java.beans.PropertyDescriptor;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;

import fr.ele.core.search.NumberOperator;
import fr.ele.core.search.NumberValueCriteria;
import fr.ele.core.search.Search;
import fr.ele.core.search.SearchOperator;
import fr.ele.core.search.StringOperator;
import fr.ele.core.search.StringValueCriteria;

public class SearchToUi {
    public static <T extends Search> List<UiCriteria> transform(
            String pathPrefix, Class<T> clazz) {
        List<UiCriteria> criterias = new LinkedList<UiCriteria>();
        PropertyDescriptor[] properties = BeanUtils
                .getPropertyDescriptors(clazz);
        for (PropertyDescriptor property : properties) {
            String name = property.getName();
            if (!name.equals("class")) {
                String path = createPath(pathPrefix, name);
                String title = WordUtils.capitalize(name);
                if (StringValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    UiCriteria criteria = createCriteria(path, title,
                            ValueType.STRING, StringOperator.values());
                    criterias.add(criteria);
                } else if (NumberValueCriteria.class.isAssignableFrom(property
                        .getPropertyType())) {
                    UiCriteria criteria = createCriteria(path, title,
                            ValueType.NUMBER, NumberOperator.values());
                    criterias.add(criteria);
                }
            }
        }
        return criterias;
    }

    private static String createPath(String pathPrefix, String name) {
        if (StringUtils.isNotEmpty(pathPrefix)) {
            StringBuilder sb = new StringBuilder(pathPrefix.length()
                    + name.length() + 1);
            return sb.append(pathPrefix).append('.').append(name).toString();
        }
        return name;
    }

    private static UiCriteria createCriteria(String path, String title,
            ValueType type, SearchOperator... operators) {
        UiCriteria criteria = new UiCriteria();
        criteria.setName(path);
        criteria.setTitle(title);
        criteria.setType(type);
        criteria.addOperators(operators);
        return criteria;
    }
}
