package fr.ele;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import fr.ele.core.ApplicationProfiles;

@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class,
        MetricRepositoryAutoConfiguration.class})
public class WebApp {

    private static final Logger LOG = LoggerFactory.getLogger(WebApp.class);

    @Inject
    private Environment env;

    /**
     * Initializes stack.
     * <p/>
     * Spring profiles can be configured with a program arguments
     * --spring.profiles.active=your-active-profile
     * <p/>
     */
    @PostConstruct
    public void initApplication() {
        if (env.getActiveProfiles().length == 0) {
            LOG.warn("No Spring profile configured, running with default configuration");
        } else {
            for (String profile : env.getActiveProfiles()) {
                LOG.info("Running with Spring profile(s) : {}", profile);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(WebApp.class);
        app.setShowBanner(false);

        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(
                args);

        // Check if the selected profile has been set as argument.
        // if not the development profile will be added
        addDefaultProfile(app, source);
        app.run(args);
    }

    /**
     * Set a default profile if it has not been set
     */
    private static void addDefaultProfile(SpringApplication app,
            SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")) {
            app.setAdditionalProfiles(ApplicationProfiles.TEST);
        }
    }

}
