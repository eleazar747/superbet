package fr.ele.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import fr.ele.core.ApplicationProfiles;

@Configuration
@EnableAsync
@EnableScheduling
@Profile(ApplicationProfiles.ASYNC)
public class AsyncConfiguration implements AsyncConfigurer, EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        propertyResolver = new RelaxedPropertyResolver(environment, "async.");
    }

    @Override
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(propertyResolver.getProperty("corePoolSize", Integer.class, 2));
        executor.setMaxPoolSize(propertyResolver.getProperty("corePoolSize", Integer.class, 50));
        executor.setQueueCapacity(propertyResolver.getProperty("corePoolSize", Integer.class, 10000));
        executor.setThreadNamePrefix("stack-Executor-");
        executor.initialize();
        return executor;
    }
}
