package fr.ele;

import java.beans.PropertyDescriptor;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ele.core.search.criteria.enums.EnumValueCriteria;
import fr.ele.core.search.ui.SearchToUi;
import fr.ele.model.RefEntityType;
import fr.ele.model.search.BookmakerSearch;

public class FormJsonTest {

    @Test
    public void testSearch() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(System.out,
                SearchToUi.transform(BookmakerSearch.class));
    }

    @Test
    public void testReadSearch() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        BookmakerSearch value = mapper
                .readValue(
                        "{\"code\":{\"operator\":\"EQ\",\"criteriaValue\":\"hghjgkj\"},\"id\":{\"operator\":\"EQ\"},\"url\":{\"operator\":\"EQ\"},\"zizicoptere\":{\"operator\":\"EQ\"}}",
                        BookmakerSearch.class);
        value.toString();
    }

    @Test
    public void testReflection() {
        EnumValueCriteria<RefEntityType> type = new EnumValueCriteria<RefEntityType>();
        PropertyDescriptor[] propertyDescriptors = BeanUtils
                .getPropertyDescriptors(type.getClass());
        System.out.println(propertyDescriptors.length);
    }
}
