package net.paguo.controller;

import net.paguo.controller.exception.ControllerException;
import net.paguo.controller.exception.JournalAuthenticationException;
import net.paguo.dao.LocalRoleDao;
import net.paguo.dao.LocalUserDao;
import net.paguo.domain.users.LocalRole;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.UserPermission;
import net.paguo.web.wicket.auth.Authority;
import net.paguo.web.wicket.auth.UserView;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: slava
 * Date: 15.11.2006
 * Time: 1:19:47
 * Version: $Id$
 */
public class UsersController implements Controller<LocalUser>{
    public static final Log log = LogFactory.getLog(UsersController.class);
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
        log.info("About to save " + user);
        try{
            if (user.getId() == null){
                log.info("New object");
                UserPermission permissionEntry = user.getPermissionEntry();
                user.getPermissionEntry().setDigest(DigestUtils.shaHex(permissionEntry.getDigest()));
                getUsersDao().create(user);
            }else{
                log.info("Updating object");
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

    public UserView authenticate(String login, String password)
            throws JournalAuthenticationException{
        password = DigestUtils.shaHex(password);
        List<LocalUser> usersFound = getUsersDao().findByPermission(login);
        if (usersFound == null || usersFound.size()
                == 0){
            log.info("User " + login + " not found");
            throw new JournalAuthenticationException();
        }
        if (usersFound.size() > 1){
            log.info("Too many users with username " + login);
            throw new JournalAuthenticationException();
        }
        LocalUser userFound = usersFound.iterator().next();
        if (! userFound.getPermissionEntry().getDigest().equals(password)){
            log.info("Bad password");
            throw new JournalAuthenticationException();
        }
        UserView result = new UserView(login);
        Set<Authority> roles = new HashSet<Authority>();
        result.setAuthorities(roles);
        for (LocalRole role : userFound.getRoles()) {
            roles.add(new Authority(role.getRole()));
        }
        return result;
    }
}
