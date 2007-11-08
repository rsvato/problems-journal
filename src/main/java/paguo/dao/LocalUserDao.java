package net.paguo.dao;

import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.UserPermission;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * @version $Id $
 */
public interface LocalUserDao extends GenericDao<LocalUser, Integer> {
    public List<LocalUser> findByPermission(String username);
}
