package net.paguo.dao;

import net.paguo.domain.users.LocalRole;
import net.paguo.generic.dao.GenericDao;

/**
 * @version $Id $
 */
public interface LocalRoleDao extends GenericDao<LocalRole, Integer> {
    LocalRole findByName(String name);
}
