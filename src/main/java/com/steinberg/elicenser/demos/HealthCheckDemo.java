/*
 * 18.04.2013
*/
package com.steinberg.elicenser.demos;

import com.yammer.metrics.MetricRegistry;
import com.yammer.metrics.health.HealthCheck;
import java.util.Random;

/**
 * Demo for a typical yammer Healthcheck which exposes its result to the
 * Adminservlet.
 *
 * @author Joachim Arrasz arrasz@synyx.de
 */
public class HealthCheckDemo extends HealthCheck {

    public HealthCheckDemo() {
    }

    @Override
    protected Result check() throws Exception {
        if (new Random().nextInt() % 2 == 0) {
            return HealthCheck.Result.unhealthy("EVEN Random");
        } else {
            return HealthCheck.Result.healthy("ODD Random");
        }
    }
}
