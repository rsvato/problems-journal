package net.paguo.web.lifecycle;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalRole;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collection;

/**
 * User: sreentenko
 * Date: 12.12.2007
 * Time: 1:23:28
 */
public class BootstrapListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();
        final ApplicationContext applicationContext = (ApplicationContext) context.
                getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (applicationContext == null){
            throw new IllegalStateException("No application context initialized");
        }

        UsersController controller = (UsersController) applicationContext.getBean("usersController");
        final Collection<LocalRole> roleCollection = controller.getAllRoles();
        if (roleCollection == null || roleCollection.isEmpty()){
            controller.bootstrapApplication();
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
