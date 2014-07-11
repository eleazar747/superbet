package fr.ele.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fr.ele.core.ApplicationProfiles;
import fr.ele.ui.mvc.MustacheViewResolver;

@Configuration
@Profile(ApplicationProfiles.WEB)
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Bean
    public ViewResolver resolver() {
        LOG.debug("Registering Web MVC view resolver");
        MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setResourceBase("/WEB-INF/mustache/");
        resolver.setResourceSuffix(".mustache");
        return resolver;
    }
}
