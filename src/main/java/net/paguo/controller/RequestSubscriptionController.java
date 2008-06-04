package net.paguo.controller;

import net.paguo.dao.RequestSubscriptionDao;
import net.paguo.domain.messaging.RequestSubscription;
import net.paguo.domain.testing.ProcessStage;

import java.util.List;

/**
 * @author Reyentenko
 */
public class RequestSubscriptionController {
    RequestSubscriptionDao subscriptionDao;

    public RequestSubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    public void setSubscriptionDao(RequestSubscriptionDao dao) {
        this.subscriptionDao = dao;
    }

    public List<RequestSubscription> findSubscriptionsForStage(ProcessStage stage){
        return getSubscriptionDao().findByStage(stage);
    }
}
