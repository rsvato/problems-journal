package net.paguo.dao.impl;

import net.paguo.dao.RequestDao;
import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.apache.commons.lang.math.IntRange;

/**
 * @author Reyentenko
 */
public class RequestDaoImpl extends GenericDaoHibernateImpl<Request, Integer>
        implements RequestDao {
    public RequestDaoImpl() {
        super(Request.class);
    }

    @SuppressWarnings("unchecked")
    public List<Request> findByStatus(ProcessStage status, String orderBy, boolean asc, IntRange range){
        return internalFind(status, orderBy, asc, range);
    }

    private List<Request> internalFind(ProcessStage status, String orderBy, boolean asc, IntRange range) {
        Criteria criteria = getSession().createCriteria(Request.class);
        if (status != null) {
            criteria.add(Restrictions.eq("currentStage", status));
        }
        if (orderBy == null){
            orderBy = "creationDate";
        }
        Order order = asc ? Order.asc(orderBy) : Order.desc(orderBy);
        criteria.addOrder(order);
        if (range != null){
            criteria.setMaxResults(range.getMaximumInteger() - range.getMinimumInteger());
            criteria.setFirstResult(range.getMinimumInteger());
        }
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Request> findAll(String orderBy, boolean asc, IntRange range){
        return internalFind(null, orderBy, asc, range);
    }

    public Integer countAll() {
        return internalCount(null);
    }

    public Integer countWithStage(ProcessStage status) {
        return internalCount(status);
    }

    private Integer internalCount(ProcessStage stage) {
        Criteria criteria = getSession().createCriteria(Request.class);
        if (stage != null){
           criteria.add(Restrictions.eq("currentStage", stage));
        }
        criteria.setProjection(Projections.count("id"));
        return (Integer) criteria.uniqueResult();
    }
}
