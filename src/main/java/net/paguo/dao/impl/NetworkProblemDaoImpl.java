package net.paguo.dao.impl;

import net.paguo.dao.NetworkProblemDao;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;
import org.apache.commons.lang.math.IntRange;

import java.util.List;
import java.util.Collection;
import java.util.Date;

/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 1:33:08
 */
public class NetworkProblemDaoImpl extends GenericDaoHibernateImpl<NetworkProblem, Integer>
        implements NetworkProblemDao {
    public NetworkProblemDaoImpl() {
        super(NetworkProblem.class);
    }

    @SuppressWarnings("unchecked")
    public List<NetworkProblem> findAll() {
        return getSession().createCriteria(NetworkProblem.class).list();
    }

    @SuppressWarnings("uncheked")
    public List<NetworkProblem> findOpen() {
        return getSession().getNamedQuery("NetworkProblem.findOpen").list();
    }

    public List<NetworkProblem> findAll(IntRange p0) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<NetworkProblem> findByDateRange(Date start, Date end) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
