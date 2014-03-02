package fr.ele.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import ro.isdc.wro.http.ConfigurableWroFilter;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;

import fr.ele.core.ApplicationProfiles;
import fr.ele.core.web.CachingHttpHeadersFilter;
import fr.ele.core.web.StaticResourcesProductionFilter;
import fr.ele.core.web.gzip.GZipServletFilter;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@AutoConfigureAfter(CacheConfiguration.class)
@Profile(ApplicationProfiles.WEB)
public class WebConfigurer implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    @Inject
    private Environment env;

    @Inject
    private MetricRegistry metricRegistry;

    @Inject
    private HealthCheckRegistry healthCheckRegistry;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Web application configuration, using profiles: {}",
                env.getActiveProfiles());
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST,
                DispatcherType.FORWARD, DispatcherType.ASYNC);

        initMetrics(servletContext, disps);
        initWroFilter(servletContext, disps);
        if (env.acceptsProfiles(ApplicationProfiles.PROD)) {
            initStaticResourcesProductionFilter(servletContext, disps);
            initCachingHttpHeadersFilter(servletContext, disps);
        }
        initGzipFilter(servletContext, disps);
        initApacheCxfServlet(servletContext);
        log.info("Web application fully configured");
    }

    private void initApacheCxfServlet(ServletContext servletContext) {
        log.debug("Registering CXF Servlet");
        CXFServlet cxfServlet = new CXFServlet();
        ServletRegistration.Dynamic appServlet = servletContext.addServlet(
                "CXFServlet", cxfServlet);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/rest/*");
    }

    private void initWroFilter(ServletContext servletContext,
            EnumSet<DispatcherType> disps) {

        ConfigurableWroFilter filter = new ConfigurableWroFilter();
        filter.setDebug(LoggerFactory.getLogger("ro.isdc.wro").isDebugEnabled());
        FilterRegistration.Dynamic wroFilter = servletContext.addFilter(
                "wroFilter", filter);
        wroFilter.addMappingForUrlPatterns(disps, true, "/wro/*");
        wroFilter.setAsyncSupported(true);
    }

    /**
     * Initializes the GZip filter.
     */
    public void initGzipFilter(ServletContext servletContext,
            EnumSet<DispatcherType> disps) {
        log.debug("Registering GZip Filter");

        FilterRegistration.Dynamic compressingFilter = servletContext
                .addFilter("gzipFilter", new GZipServletFilter());
        Map<String, String> parameters = new HashMap<String, String>();

        compressingFilter.setInitParameters(parameters);

        compressingFilter.addMappingForUrlPatterns(disps, true, "*.css");
        compressingFilter.addMappingForUrlPatterns(disps, true, "*.json");
        compressingFilter.addMappingForUrlPatterns(disps, true, "*.html");
        compressingFilter.addMappingForUrlPatterns(disps, true, "*.js");
        compressingFilter.addMappingForUrlPatterns(disps, true, "/app/rest/*");
        compressingFilter.addMappingForUrlPatterns(disps, true, "/metrics/*");

        compressingFilter.setAsyncSupported(true);
    }

    /**
     * Initializes the static resources production Filter.
     */
    public void initStaticResourcesProductionFilter(
            ServletContext servletContext, EnumSet<DispatcherType> disps) {

        log.debug("Registering static resources production Filter");
        FilterRegistration.Dynamic staticResourcesProductionFilter = servletContext
                .addFilter("staticResourcesProductionFilter",
                        new StaticResourcesProductionFilter());

        staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true,
                "/static/**");
        staticResourcesProductionFilter.setAsyncSupported(true);
    }

    /**
     * Initializes the cachig HTTP Headers Filter.
     */
    public void initCachingHttpHeadersFilter(ServletContext servletContext,
            EnumSet<DispatcherType> disps) {
        log.debug("Registering Cachig HTTP Headers Filter");
        FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext
                .addFilter("cachingHttpHeadersFilter",
                        new CachingHttpHeadersFilter());

        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true,
                "/static/**");
        cachingHttpHeadersFilter.setAsyncSupported(true);
    }

    /**
     * Initializes Metrics.
     */
    private void initMetrics(ServletContext servletContext,
            EnumSet<DispatcherType> disps) {
        log.debug("Initializing Metrics registries");
        servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE,
                metricRegistry);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY,
                metricRegistry);
        servletContext.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY,
                healthCheckRegistry);

        log.debug("Registering Metrics Filter");
        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter(
                "webappMetricsFilter", new InstrumentedFilter());

        metricsFilter.addMappingForUrlPatterns(disps, true, "/*");
        metricsFilter.setAsyncSupported(true);

        log.debug("Registering Metrics Servlet");
        ServletRegistration.Dynamic metricsAdminServlet = servletContext
                .addServlet("metricsServlet", new MetricsServlet());

        metricsAdminServlet.addMapping("/metrics/metrics/*");
        metricsAdminServlet.setAsyncSupported(true);
        metricsAdminServlet.setLoadOnStartup(2);

        log.debug("Registering HealthCheck Servlet");
        ServletRegistration.Dynamic healthCheckServlet = servletContext
                .addServlet("healthCheckServlet", new HealthCheckServlet());

        healthCheckServlet.addMapping("/metrics/healthcheck/*");
        healthCheckServlet.setAsyncSupported(true);
        healthCheckServlet.setLoadOnStartup(2);
    }

}
