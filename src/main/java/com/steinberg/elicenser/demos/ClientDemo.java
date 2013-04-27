/*
 * 19.04.2013
 */
package com.steinberg.elicenser.demos;

import com.yammer.metrics.ConsoleReporter;
import com.yammer.metrics.Counter;
import com.yammer.metrics.JmxReporter;
import com.yammer.metrics.MetricRegistry;
import com.yammer.metrics.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates how to use yammer metrics within clients.
 *
 * @author Joachim Arrasz
 */
public class ClientDemo {

    static final MetricRegistry metrics = new MetricRegistry("Steinberg");
    static ConsoleReporter reporter;
    static JmxReporter jmxReporter;
    static Counter evictions;
    static Timer request;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("ClientDemo");

        createReporters();
        
        createMetrics();
        
        createDemoData();
        
        showDemoDatainJMX(evictions);
        
        System.out.println("Client Demo ended");
    }

    private static void showDemoDatainJMX(final Counter evictions) throws InterruptedException {
        evictions.inc();
        Thread.sleep(1500);
        evictions.inc(3);
        Thread.sleep(1500);
        evictions.dec();
        Thread.sleep(1500);
        evictions.dec(2);
        Thread.sleep(1500);
    }

    private static void createReporters() {
        reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        
        jmxReporter = JmxReporter.forRegistry(metrics).build();

        reporter.start(1, TimeUnit.MINUTES);
        jmxReporter.start();
    }

    private static void createMetrics() {
        evictions = metrics.counter(MetricRegistry.name(HealthCheckDemo.class, "cache-evictions"));
        request = metrics.timer(MetricRegistry.name(ArithmeticDemoOperation.class, "calculation-duration"));
    }

    private static void createDemoData() throws InterruptedException {
        final Timer.Context ctx = request.time();
        new ArithmeticDemoOperation().calculateSomeMagic();
        ctx.stop();
    }
}
