package net.paguo.messaging.impl;

import net.paguo.messaging.INotifier;
import net.paguo.dao.RequestSubscriptionDao;
import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.messaging.RequestSubscription;
import net.paguo.domain.users.LocalUser;
import net.paguo.controller.RequestSubscriptionController;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Reyentenko
 */
public class TestingRequestNotifierImpl implements INotifier<Request> {
    RequestSubscriptionController controller;
    private static final Log log = LogFactory.getLog(TestingRequestNotifierImpl.class);

    public RequestSubscriptionController getController() {
        return controller;
    }

    public void setController(RequestSubscriptionController controller) {
        this.controller = controller;
    }

    public final void doNotify(Request req) {
        final ProcessStage currentStage = req.getCurrentStage();
        final List<RequestSubscription> forStage = getController().findSubscriptionsForStage(currentStage);
        if (! forStage.isEmpty()){
            final RequestSubscription subscription = forStage.iterator().next();
            final Set<LocalUser> users = subscription.getSubscribers();
            log.debug(users.size() + " subscribed to event");
            if (! users.contains(req.getAuthor())){
                log.debug("Author not subscribed");
            }
        }
    }
}
