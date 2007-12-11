package net.paguo.controller;

import net.paguo.controller.exception.ControllerException;
import net.paguo.controller.exception.JournalAuthenticationException;
import net.paguo.dao.LocalGroupDao;
import net.paguo.dao.LocalRoleDao;
import net.paguo.dao.LocalUserDao;
import net.paguo.domain.users.LocalGroup;
import net.paguo.domain.users.LocalRole;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.UserPermission;
import net.paguo.web.wicket.auth.Authority;
import net.paguo.web.wicket.auth.UserView;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

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
    private LocalGroupDao groupDao;

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

    public LocalGroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(LocalGroupDao groupDao) {
        this.groupDao = groupDao;
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


    public LocalUser findByPermission(String username) throws JournalAuthenticationException {
        List<LocalUser> found = getUsersDao().findByPermission(username);
        if (found == null || found.isEmpty()){
            log.info("User " + username + " not found");
            throw new JournalAuthenticationException();
        }
        if (found.size() > 1){
            log.info("Too many users with username " + username);
            throw new JournalAuthenticationException();
        }
        return found.iterator().next();
    }

    public UserView authenticate(String login, String password)
            throws JournalAuthenticationException{
        password = DigestUtils.shaHex(password);
        LocalUser userFound = findByPermission(login);
        if (! userFound.getPermissionEntry().getDigest().equals(password)){
            log.info("Bad password");
            throw new JournalAuthenticationException();
        }
        UserView result = new UserView(login);
        Set<Authority> roles = new HashSet<Authority>();

        for (String s : userFound.getAuthorities()) {
            roles.add(new Authority(s));
        }

        result.setAuthorities(roles);

        return result;
    }


    public List<LocalGroup> getAllGroups(){
        return getGroupDao().readAll();
    }

    public List<LocalGroup> getGroups(int from, int count, boolean asc){
        return getGroupDao().readPart(count, from, "groupName", asc);
    }

    public LocalGroup getGroup(int groupId) {
        return getGroupDao().read((long) groupId);
    }

    public void createGroup(LocalGroup group) throws ControllerException{
        try {
            getGroupDao().create(group);
        } catch (HibernateException e) {
            throw new ControllerException(e);
        }
    }

    public void updateGroup(LocalGroup group) throws ControllerException {
        try {
            getGroupDao().update(group);
        } catch (HibernateException e) {
            throw new ControllerException(e);
        }
    }

    public void deleteGroup(LocalGroup grp) throws ControllerException{
        try {
            getGroupDao().delete(grp);
        } catch (HibernateException e) {
            throw new ControllerException(e);
        }
    }

    public void saveOrUpdateUser(LocalUser user) throws ControllerException{
        try{
            if (user.getId() == null){
                getUsersDao().create(user);
            }else{
                getUsersDao().update(user);
            }
        }catch (HibernateException e){
            throw new ControllerException(e);
        }
    }

    public LocalUser findUser(int userId) {
        return getUsersDao().read(userId);
    }

    public void deleteUser(LocalUser user) throws ControllerException{
        try{
            getUsersDao().delete(user);
        }catch (HibernateException e){
            throw  new ControllerException(e);
        }
    }
}
