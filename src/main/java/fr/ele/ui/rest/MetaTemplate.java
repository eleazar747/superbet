package fr.ele.ui.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@NameBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MetaTemplate {
    public static class Dummy {

    }

    String template() default "";

    Class<?> entityClass() default Dummy.class;

    String profile() default "";
}
