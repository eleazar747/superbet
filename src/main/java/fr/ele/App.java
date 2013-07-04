package fr.ele;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.ele.core.ApplicationProfiles;

public class App {

    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    public static void main(String[] args) throws Throwable {
        String profile = System.getProperty(SPRING_PROFILES_ACTIVE);
        if (profile == null || profile.isEmpty()) {
            System.setProperty(SPRING_PROFILES_ACTIVE, ApplicationProfiles.TEST);
        }
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
                "ApplicationContext.xml");
        try {
            ctx.registerShutdownHook();
            ctx.start();
        } finally {
            // ctx.close();
        }
    }
}
