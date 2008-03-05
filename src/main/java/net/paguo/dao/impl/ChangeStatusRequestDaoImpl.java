package net.paguo.dao.impl;

import net.paguo.dao.ChangeStatusRequestDao;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.RequestInformationType;
import net.paguo.domain.requests.ChangeRequestType;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.math.IntRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

/**
 * User: sreentenko
 * Date: 18.02.2008
 * Time: 1:47:02
 */
public class ChangeStatusRequestDaoImpl extends GenericDaoHibernateImpl<ChangeStatusRequest, Integer> implements ChangeStatusRequestDao {
    private static final Log log = LogFactory.getLog(ChangeStatusRequestDaoImpl.class);

    //awful
    private static final Map<RequestInformationType, Map<ChangeRequestType, String>> paramsToFields;

    static {
        paramsToFields = new HashMap<RequestInformationType, Map<ChangeRequestType, String>>();

        //request part
        Map<ChangeRequestType, String> reqMap = new HashMap<ChangeRequestType, String>();
        reqMap.put(ChangeRequestType.CANCEL, "cancelRequest");
        reqMap.put(ChangeRequestType.RESTORE, "restoreRequest");
        paramsToFields.put(RequestInformationType.REQUEST, reqMap);

        //responsePart
        Map<ChangeRequestType, String> respMap = new HashMap<ChangeRequestType, String>();
        respMap.put(ChangeRequestType.CANCEL, "cancelExec");
        respMap.put(ChangeRequestType.RESTORE, "restoreExec");

        paramsToFields.put(RequestInformationType.RESPONSE, respMap);
    }

    public ChangeStatusRequestDaoImpl() {
        super(ChangeStatusRequest.class);
    }

    @SuppressWarnings("unchecked")
    private List<ChangeStatusRequest> generalFileOrderedByDate(IntRange range,
                                                       RequestInformationType infoType,
                                                       ChangeRequestType requestType,
                                                       boolean asc) {
        final Criteria criteria = getSession().createCriteria(ChangeStatusRequest.class);

        String propertyName = findProperty(infoType, requestType) + ".dateEntered";

        log.debug("Got property name" + propertyName);
        Order order = asc ? Order.asc(propertyName) : Order.desc(propertyName);
        criteria.addOrder(order);
        rangeCriteria(range, criteria);
        return criteria.list();

    }

    static String findProperty(RequestInformationType infoType, ChangeRequestType requestType) {
        return paramsToFields.get(infoType).get(requestType);
    }

    private void rangeCriteria(IntRange range, Criteria criteria) {
        criteria.setFirstResult(range.getMinimumInteger());
        criteria.setMaxResults(range.getMaximumInteger());
    }

    private void rangeQuery(IntRange range, Query criteria) {
        criteria.setFirstResult(range.getMinimumInteger());
        criteria.setMaxResults(range.getMaximumInteger());
    }

    public List<ChangeStatusRequest> findOrderedByDateDesc(IntRange range, RequestInformationType infoType, ChangeRequestType requestType) {
        return generalFileOrderedByDate(range, infoType, requestType, false);
    }

    public List<ChangeStatusRequest> findOrderedByDate(IntRange range,
                                                       RequestInformationType infoType,
                                                       ChangeRequestType requestType) {
        return generalFileOrderedByDate(range, infoType, requestType, true);

    }

    @SuppressWarnings("unchecked")
    public List<ChangeStatusRequest> generalFindOrderedByClient(IntRange range, boolean asc) {
        final Criteria criteria = getSession().createCriteria(ChangeStatusRequest.class);
        Order orderClient = asc ? Order.asc("client") : Order.desc("client");
        Order orderLabel = asc ? Order.asc("enteredClient") : Order.desc("enteredClient");
        criteria.addOrder(orderClient);
        criteria.addOrder(orderLabel);
        rangeCriteria(range, criteria);
        return criteria.list();

    }

    @SuppressWarnings("unchecked")
    public List<ChangeStatusRequest> findOrderedByClient(IntRange range) {
        final Query query = getSession().getNamedQuery("ChangeStatusRequest.findOrderedByClient");
        rangeQuery(range, query);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<ChangeStatusRequest> findOrderedByClientDesc(IntRange range) {
        final Query query = getSession().getNamedQuery("ChangeStatusRequest.findOrderedByClientDesc");
        rangeQuery(range, query);
        return query.list();
    }
}
