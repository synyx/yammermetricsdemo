/*
 * 18.04.2013
 */
package com.steinberg.elicenser.demos.servlets;

import com.steinberg.elicenser.demos.HealthCheckDemo;
import com.yammer.metrics.health.HealthCheckRegistry;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Bootstraps all relevant things needed by yammer metrics.
 *
 * @author Joachim Arrasz
 */
@WebListener
public class Bootstrapper implements ServletContextListener {

    private HealthCheckRegistry healthChecks;

    private void registerChecks() {
        healthChecks.register("demo", new HealthCheckDemo());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInit");
        healthChecks = new HealthCheckRegistry();
        
        sce.getServletContext().setAttribute("com.yammer.metrics.servlet.HealthCheckServlet.registry", healthChecks);
        registerChecks();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //TODO unregister and destroy ...
    }
}
