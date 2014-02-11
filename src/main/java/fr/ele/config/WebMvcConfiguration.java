package fr.ele.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fr.ele.ui.mvc.MustacheViewResolver;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory
            .getLogger(WebMvcConfiguration.class);

    @Bean
    public ViewResolver resolver() {
        LOG.debug("Registering Web MVC view resolver");
        MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setResourceBase("/WEB-INF/mustache/");
        resolver.setResourceSuffix(".mustache");
        return resolver;
    }
}
