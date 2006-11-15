package net.paguo.web.lifecycle;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * User: slava
 * Date: 15.11.2006
 * Time: 1:03:42
 * Version: $Id$
 */
public class RedeployListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<java.sql.Driver> e = DriverManager.getDrivers();
        while(e.hasMoreElements()){
            Driver d = e.nextElement();
            try {
                DriverManager.deregisterDriver(d);
            } catch (SQLException e1) {
                ServletContext servletContext = servletContextEvent.getServletContext();
                servletContext.log(new StringBuilder().append("Error deregistering driver ")
                        .append(d).append(": ").append(e1).toString());
            }
        }
    }
}
