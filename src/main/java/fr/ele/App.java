package fr.ele;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) throws Throwable {
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
