package net.paguo.listeners;

import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.paguo.domain.testing.Request;

/**
 * @author Reyentenko
 */
public class TestingRequestEventListener implements
    org.hibernate.event.PostDeleteEventListener, org.hibernate.event.PostInsertEventListener, org.hibernate.event.PostUpdateEventListener{

    private static final Log log = LogFactory.getLog(TestingRequestEventListener.class);
    private static final long serialVersionUID = -2144161022858355466L;

    public void onPostDelete(PostDeleteEvent event) {
        final Object o = event.getEntity();
        if (o instanceof Request){
           log.debug("Deleted item is request");
        }
    }

    public void onPostInsert(PostInsertEvent event) {
        final Object o = event.getEntity();
        log.debug(o.getClass());
        if (o instanceof Request){
           log.debug("Inserted item is request");
        }
    }

    public void onPostUpdate(PostUpdateEvent event) {
        final Object o = event.getEntity();
        log.debug(o.getClass());
        if (o instanceof Request){
           log.debug("Inserted item is request");
        }
    }

}
