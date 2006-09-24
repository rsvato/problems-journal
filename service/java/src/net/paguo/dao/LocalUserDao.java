package net.paguo.dao;

import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.UserPermission;
import net.paguo.generic.dao.GenericDao;

/**
 * @version $Id $
 */
public interface LocalUserDao extends GenericDao<LocalUser, Integer> {
    public LocalUser findByPermission(UserPermission permission);
}
