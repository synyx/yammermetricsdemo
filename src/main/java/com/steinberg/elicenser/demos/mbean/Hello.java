/*
 */
package com.steinberg.elicenser.demos.mbean;

/**
 *
 * @author Joachim Arrasz
 */
public class Hello implements HelloMBean {

    @Override
    public void sayHello() {
        System.out.println("HELLO");
    }
    
}
