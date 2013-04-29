/*
 * 18.04.2013
 */
package org.synyx.demos.listener;

import org.synyx.demos.ArithmeticDemoOperation;
import org.synyx.demos.HealthCheckDemo;
import org.synyx.demos.listener.mbean.Hello;
import org.synyx.demos.listener.mbean.HelloMBean;
import com.yammer.metrics.Counter;
import com.yammer.metrics.JmxReporter;
import com.yammer.metrics.MetricRegistry;
import com.yammer.metrics.Timer;
import com.yammer.metrics.health.HealthCheckRegistry;
import com.yammer.metrics.servlet.DefaultWebappMetricsFilter;
import com.yammer.metrics.servlets.HealthCheckServlet;
import com.yammer.metrics.servlets.MetricsServlet;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
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
    private static final String MBEAN_NAME = "com.demonstration.example:type=Hello";

    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    private HealthCheckRegistry healthChecks;
    private MetricRegistry metrics;
    private MetricRegistry metricsForWebapp;
    private JmxReporter jmx;
    private JmxReporter jmxForWebapp;
    private static Counter evictions;
    private static Timer request;

    private void registerHealthChecks() {
        healthChecks.register("demo", new HealthCheckDemo());

        evictions = metrics.counter(MetricRegistry.name(HealthCheckDemo.class, "cache-evictions"));
        request = metrics.timer(MetricRegistry.name(ArithmeticDemoOperation.class, "calculation-duration"));

    }

    /**
     * THIS is just a little Helper for easy demonstration purposes.
     */
    public static void createDemoDataInJmx() {
        evictions.inc(3);
        request.update(471, TimeUnit.SECONDS);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        createAndBuildRegistries(sce);

        addingStandardMBeanDemo();

        exposeFoundMBeanDomains();

        registerHealthChecks();


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        jmx.stop();
        jmxForWebapp.stop();
        removeStandardMBeanDemo();

    }

    private void addingStandardMBeanDemo() {

        final HelloMBean mBean = new Hello();
        try {
            mBeanServer.registerMBean(mBean, new ObjectName(MBEAN_NAME));
        } catch (MalformedObjectNameException | NullPointerException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException ex) {
            System.out.println(ex.getMessage()); //just a demo, so it's okay
        }
    }

    private void createAndBuildRegistries(ServletContextEvent sce) {
        healthChecks = new HealthCheckRegistry();
        metrics = new MetricRegistry("DemonstrationMetrics");
        metricsForWebapp = new MetricRegistry("DemonstrationWebapp");

        jmx = JmxReporter.forRegistry(metrics).build();
        jmxForWebapp = JmxReporter.forRegistry(metricsForWebapp).build();

        sce.getServletContext().setAttribute(HealthCheckServlet.class.getCanonicalName() + ".registry", healthChecks);
        sce.getServletContext().setAttribute(MetricsServlet.class.getCanonicalName() + ".registry", metrics);
        sce.getServletContext().setAttribute(DefaultWebappMetricsFilter.class.getName() + ".registry", metricsForWebapp);

        jmx.start();
        jmxForWebapp.start();
    }

    private void exposeFoundMBeanDomains() {
        String[] domains = mBeanServer.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("Domain: " + domains[i]);

        }
    }

    private void removeStandardMBeanDemo() {
        try {
            mBeanServer.unregisterMBean(new ObjectName(MBEAN_NAME));
        } catch (InstanceNotFoundException | MBeanRegistrationException | MalformedObjectNameException | NullPointerException ex) {
            System.out.println(ex.getMessage()); //just a demo, so it's okay
        }
    }
}
