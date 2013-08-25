package fr.ele;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;

import fr.ele.core.ApplicationProfiles;

public class WebApp {

    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    private static final String WEB_XML = "/WEB-INF/web.xml";

    public static void main(String[] args) throws Exception {
        String profile = System.getProperty(SPRING_PROFILES_ACTIVE);
        if (profile == null || profile.isEmpty()) {
            System.setProperty(SPRING_PROFILES_ACTIVE, ApplicationProfiles.TEST);
        }
        Server server = new Server(8080);
        WebAppContext context = new WebAppContext();
        // context.setDescriptor("/WEB-INF/web.xml");
        context.setWar(getShadedWarUrl());
        // context.setResourceBase(".");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{context});

        server.setHandler(context);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getShadedWarUrl() {
        String _urlStr = WebApp.class.getResource(WEB_XML).toString();
        // Strip off "WEB-INF/web.xml"
        return _urlStr.substring(0, _urlStr.length() - 15);
    }
}
