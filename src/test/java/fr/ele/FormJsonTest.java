package fr.ele;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.function.Consumer;
import reactor.function.Function;

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

    @Test
    @Ignore
    public void test() {
        List<Integer> ints = new ArrayList<Integer>(100);
        for (int i = 0; i < 100; i++) {
            ints.add(i);
        }
        Deferred<Integer, Stream<Integer>> deferred = Streams
                .<Integer> defer(ints).env(new Environment())
                .dispatcher(Environment.THREAD_POOL).get();
        Stream<Integer> stream = deferred.compose();
        stream.consume(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) {
                System.out.println(t);
            }
        }).map(new Function<Integer, String>() {

            @Override
            public String apply(Integer t) {
                return "yes" + t.toString();
            }
        }).consume(new Consumer<String>() {

            @Override
            public void accept(String t) {
                System.out.println(t);
            }
        }).flush();
    }
}
