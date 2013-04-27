/*
 */
package com.steinberg.elicenser.demos.mbean;

import com.steinberg.elicenser.demos.servlets.Bootstrapper;

/**
 * Just a MBean for demonstration Purposes of yammer.
 * 
 * @author Joachim Arrasz
 */
public class Hello implements HelloMBean {

    @Override
    public void sayHello() {
        System.out.println("HELLO");
    }

    @Override
    public void incrementCounter() {

        Bootstrapper.createDemoDataInJmx();
    }
}
