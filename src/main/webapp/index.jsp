<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yammer Metrics Demo</title>
    </head>
    <body>
        <h1>Welcome to yammer metrics Demo.</h1>
        <a href="../YammerMetricsDemo/admin">Inspect JSON Support</a>
        <p>To inspect JMX Support just open a tool like jVisualVM or jConsole and 
            connect to Tomcats instance or the client instance (if you started ClientDemo.java)
        </p>
        <p>There you will find an entry called "Demonstration". Have Fun!</p>
        <p>Furthermore there is a com.demonstration.example entry with some operation.
            If you choose incrementCounter operation you can see some modifications 
            in com.example.demos.HealthCheckDemo.cache-evictions:counter</p>
        <p>TODO:</p>
        <ul>
            <li>Add a Gauge Demo</li>
            <li>Add a Histogram Demo</li>
            <li>Add a Meter Demo</li>
        </ul>
    </body>
</html>
