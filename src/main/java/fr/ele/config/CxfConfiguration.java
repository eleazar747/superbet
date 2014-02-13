package fr.ele.config;

import java.util.ArrayList;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import fr.ele.config.jaxrs.RestService;

@Configuration
public class CxfConfiguration {
    private static final Logger LOG = LoggerFactory
            .getLogger(WebMvcConfiguration.class);

    @ApplicationPath("/")
    public class JaxRsApiApplication extends Application {
    }

    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer(ApplicationContext appContext) {
        LOG.info("init CXF server");
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance()
                .createEndpoint(jaxRsApiApplication(),
                        JAXRSServerFactoryBean.class);
        factory.setServiceBeans(new ArrayList<>(appContext
                .getBeansWithAnnotation(RestService.class).values()));
        factory.setAddress("/");
        factory.setProvider(jsonProvider());
        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        JacksonJsonProvider provider = new JacksonJsonProvider();
        ObjectMapper mapper = new ObjectMapper();
        AfterburnerModule module = new AfterburnerModule();
        mapper.registerModule(module);
        provider.setMapper(mapper);
        return provider;
    }

}
