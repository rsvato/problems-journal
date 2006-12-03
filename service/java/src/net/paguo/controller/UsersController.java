package net.paguo.controller;

import net.paguo.dao.LocalUserDao;
import net.paguo.dao.LocalRoleDao;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.LocalRole;
import net.paguo.domain.users.UserPermission;
import net.paguo.controller.exception.ControllerException;

import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;

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

    public Collection<LocalRole> getAllRoles(){
        return  getRolesDao().readAll();
    }

    public void saveRole(LocalRole role) throws ControllerException {
        try{
            if (role.getId() == null){
                getRolesDao().create(role);
            }else{
                getRolesDao().update(role);
            }
        }catch(Throwable t){
            throw new ControllerException(t);
        }
    }

    public LocalRole readRole(Integer roleId){
        return getRolesDao().read(roleId);
    }

    public LocalUser readUser(Integer userId){
        return getUsersDao().read(userId);
    }

    public void saveUser(LocalUser user) throws ControllerException{
        try{
            if (user.getId() == null){
                UserPermission permissionEntry = user.getPermissionEntry();
                user.getPermissionEntry().setDigest(DigestUtils.shaHex(permissionEntry.getDigest()));
                getUsersDao().create(user);
            }else{
                LocalUser oldUser = getUsersDao().read(user.getId());
                String oldDigest = oldUser.getPermissionEntry().getDigest();
                String newPassword = user.getPermissionEntry().getDigest();
                if (! oldDigest.equals(newPassword)){
                    newPassword = DigestUtils.shaHex(newPassword);
                    user.getPermissionEntry().setDigest(newPassword);
                }
                getUsersDao().update(user);
            }
        }catch(Throwable t){
            throw  new ControllerException(t);
        }
    }
}
