/*
 * 19.04.2013
 */
package com.steinberg.elicenser.demos;

import com.yammer.metrics.ConsoleReporter;
import com.yammer.metrics.Counter;
import com.yammer.metrics.JmxReporter;
import com.yammer.metrics.MetricRegistry;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("ClientDemo");
        reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        
        jmxReporter = JmxReporter.forRegistry(metrics).build();

        reporter.start(1, TimeUnit.MINUTES);
        jmxReporter.start();
        
        final Counter evictions = metrics.counter(metrics.name(HealthCheckDemo.class, "cache-evictions"));
        
        evictions.inc();
        evictions.inc(3);
        evictions.dec();
        evictions.dec(2);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(500);
            evictions.inc();
        }
        
        System.out.println("Client Demo ended");
    }
}
