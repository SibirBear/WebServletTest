package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.ArrayList;

import static org.example.Counter.initCounter;
import static org.example.ListUsers.initListUsers;

public class Main {

    public static void main(String[] args) throws LifecycleException {
        Tomcat app = getApp(getPort());
        app.start();
        app.getServer().await();

    }

    public static Tomcat getApp(int port) {
        Tomcat app = new Tomcat();
        app.setPort(port);

        initCounter();
        initListUsers();

        Context ctx = app.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        app.addServlet(ctx, WelcomeServlet.class.getSimpleName(), new WelcomeServlet());
        ctx.addServletMappingDecoded("", WelcomeServlet.class.getSimpleName());

        app.addServlet(ctx, MainServlet.class.getSimpleName(), new MainServlet());
        ctx.addServletMappingDecoded("/users/", MainServlet.class.getSimpleName());

        app.addServlet(ctx, AccountServlet.class.getSimpleName(), new AccountServlet());
        ctx.addServletMappingDecoded("/accounts/*", AccountServlet.class.getSimpleName());

        return app;
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 8091;
    }

}
