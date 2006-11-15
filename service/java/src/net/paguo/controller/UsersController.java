package net.paguo.controller;

import net.paguo.dao.LocalUserDao;
import net.paguo.dao.LocalRoleDao;
import net.paguo.domain.users.LocalUser;

import java.util.Collection;

/**
 * User: slava
 * Date: 15.11.2006
 * Time: 1:19:47
 * Version: $Id$
 */
public class UsersController implements Controller<LocalUser>{
    private LocalUserDao usersDao;
    private LocalRoleDao rolesDao;

    public LocalUserDao getUsersDao() {
        return usersDao;
    }

    public void setUsersDao(LocalUserDao usersDao) {
        this.usersDao = usersDao;
    }

    public LocalRoleDao getRolesDao() {
        return rolesDao;
    }

    public void setRolesDao(LocalRoleDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    public Collection<LocalUser> getAll(){
        return getUsersDao().readAll();
    }
}
