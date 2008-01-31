package net.paguo.dao.impl;

import net.paguo.domain.users.LocalUser;
import net.paguo.generic.dao.impl.GenericDaoHibernateImpl;
import net.paguo.dao.LocalUserDao;

import java.util.List;

/**
 * User: sreentenko
 * Date: 19.11.2007
 * Time: 1:36:20
 */
public class LocalUserDaoImpl extends GenericDaoHibernateImpl<LocalUser, Integer>
        implements LocalUserDao {
    public LocalUserDaoImpl() {
        super(LocalUser.class);
    }

    @SuppressWarnings("unchecked")
    public List<LocalUser> findByPermission(String username) {
        return getSession().getNamedQuery("LocalUser.findByPermission").
                setParameter("uname", username).list();
    }
}
