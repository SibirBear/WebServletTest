package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static Tomcat getApp(int port) {
        Tomcat app = new Tomcat();
        app.setPort(port);

        Context ctx = app.addContext("", new File(".").getAbsolutePath());

        app.addServlet(ctx, WelcomeServlet.class.getSimpleName(), new WelcomeServlet());
        ctx.addServletMappingDecoded("", MainServlet.class.getSimpleName());

        app.addServlet(ctx, MainServlet.class.getSimpleName(), new MainServlet(new ArrayList<>(), 0));
        ctx.addServletMappingDecoded("/users/", MainServlet.class.getSimpleName());

        return app;
    }

    public static void main(String[] args) throws LifecycleException {
        Tomcat app = getApp(getPort());
        app.start();
        app.getServer().await();

    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 8091;
    }

}
