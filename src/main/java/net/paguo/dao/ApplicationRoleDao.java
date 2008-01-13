package net.paguo.dao;

import net.paguo.domain.users.ApplicationRole;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * User: sreentenko
 * Date: 13.01.2008
 * Time: 2:52:10
 */
public interface ApplicationRoleDao extends GenericDao<ApplicationRole, Integer>{
    List<ApplicationRole> findByClassAndAction(String className, String action);
    List<ApplicationRole> findByClass(String className);
}
