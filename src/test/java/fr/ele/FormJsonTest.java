package fr.ele;

import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import fr.ele.model.search.BookmakerSearch;
import fr.ele.ui.search.FieldType;
import fr.ele.ui.search.Form;
import fr.ele.ui.search.FormField;
import fr.ele.ui.search.FormNode;
import fr.ele.ui.search.SearchToForm;

public class FormJsonTest {

    @Test
    public void testCriteria() throws Throwable {
        FormField criteria = new FormField();
        criteria.setType(FieldType.STRING);
        criteria.setTitle("hello");
        criteria.setDescription(" world");
        criteria.setRequiered(true);
        criteria.setValues(Arrays.asList("bonjour", "les", "gens"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(System.out, criteria);
    }

    @Test
    public void testMap() throws Throwable {
        Form form = new Form();
        FormNode schema = new FormNode();
        form.setSchema(schema);

        FormField name = new FormField();
        name.setType(FieldType.STRING);
        name.setTitle("Name");
        name.setRequiered(Boolean.TRUE);
        schema.add("name", name);

        FormField age = new FormField();
        age.setType(FieldType.NUMBER);
        age.setTitle("Age");
        schema.add("age", age);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(System.out, form);
    }

    @Test
    public void testSearch() throws Throwable {
        SearchToForm transformer = new SearchToForm();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(System.out,
                transformer.tranform(BookmakerSearch.class));
    }
}
