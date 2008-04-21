package net.paguo.dao.impl;

import net.paguo.dao.RequestDao;
import net.paguo.domain.testing.Request;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;

/**
 * @author Reyentenko
 */
public class RequestDaoImpl extends GenericDaoHibernateImpl<Request, Integer>
        implements RequestDao {
    public RequestDaoImpl() {
        super(Request.class);
    }
}
