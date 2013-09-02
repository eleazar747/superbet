package fr.ele.ui.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RequestMapping;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Activity {
    public String name();

    /**
     * If you need to override URI from {@link RequestMapping}
     * 
     * @return string represent localized URI
     */
    public String uri() default "";
}
