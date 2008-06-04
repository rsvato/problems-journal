package net.paguo.dao;

import net.paguo.domain.messaging.RequestSubscription;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * @author Reyentenko
 */
public interface RequestSubscriptionDao extends GenericDao<RequestSubscription, Integer> {
    List<RequestSubscription> findByStage(ProcessStage stage);
}
