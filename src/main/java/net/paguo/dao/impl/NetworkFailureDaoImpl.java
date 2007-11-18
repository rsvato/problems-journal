package net.paguo.dao.impl;

import net.paguo.dao.NetworkFailureDao;
import net.paguo.domain.problems.NetworkFailure;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: sreentenko
 * Date: 19.11.2007
 * Time: 1:11:12
 */
public class NetworkFailureDaoImpl
        extends GenericDaoHibernateImpl<NetworkFailure, Integer>
        implements NetworkFailureDao {
    public NetworkFailureDaoImpl() {
        super(NetworkFailure.class);
    }

    @SuppressWarnings("unchecked")
    public List<NetworkFailure> findOpen() {
        final Session session = super.getSession();
        return session.getNamedQuery("NetworkFailure.findOpen").list();
    }
}
