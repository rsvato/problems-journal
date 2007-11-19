package net.paguo.dao.impl;

import net.paguo.dao.NetworkProblemDao;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;
import org.hibernate.Query;

import java.util.List;

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
}
