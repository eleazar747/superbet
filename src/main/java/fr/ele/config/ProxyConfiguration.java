package fr.ele.config;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProxyConfiguration implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory
            .getLogger(ProxyConfiguration.class);

    private RelaxedPropertyResolver rpr;

    @Override
    public void setEnvironment(Environment environment) {
        rpr = new RelaxedPropertyResolver(environment, "spring.proxy.");
    }

    @Bean
    public Proxy getProxy() {
        LOG.info("Initialize proxy");
        if (rpr.containsProperty("url") && rpr.containsProperty("port")) {
            String url = rpr.getProperty("url");
            Integer port = rpr.getProperty("port", Integer.class);
            LOG.info("Use proxy {}:{}", url, port);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url,
                    port));
            return proxy;
        }

        LOG.info("No proxy used");
        return Proxy.NO_PROXY;
    }
}
