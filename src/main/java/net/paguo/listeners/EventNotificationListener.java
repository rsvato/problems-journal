package net.paguo.listeners;

import org.hibernate.event.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.paguo.domain.testing.Request;
import net.paguo.messaging.INotifier;

import java.util.Map;

/**
 * @author Reyentenko
 */
public class EventNotificationListener implements
    org.hibernate.event.PostDeleteEventListener, org.hibernate.event.PostInsertEventListener, PostUpdateEventListener{

    private static final Log log = LogFactory.getLog(EventNotificationListener.class);
    private static final long serialVersionUID = -2144161022858355466L;

    public void onPostDelete(PostDeleteEvent event) {
        final Object o = event.getEntity();
        if (o instanceof Request){
           log.debug("Deleted item is request");
        }
    }

    public void onPostInsert(PostInsertEvent event) {
        final Object o = event.getEntity();
        final Class message = o.getClass();
        log.debug(message);
        final INotifier notifier = getRegistry().get(message);
        if (notifier != null){
            notifier.doNotify(o);
        }
    }

    public void onPostUpdate(PostUpdateEvent event) {
        final Object o = event.getEntity();
        final Class message = o.getClass();
        log.debug(message);
        final INotifier notifier = getRegistry().get(message);
        if (notifier != null){
            notifier.doNotify(o);
        }
    }

    private Map<Class, INotifier> registry;

    public Map<Class, INotifier> getRegistry() {
        return registry;
    }

    public void setRegistry(Map<Class, INotifier> registry) {
        this.registry = registry;
    }
}
