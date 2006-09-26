package net.paguo.web.security;

import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.springframework.dao.DataAccessException;
import net.paguo.dao.LocalUserDao;
import net.paguo.dao.UserPermissionDao;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.LocalRole;
import net.paguo.domain.users.UserPermission;

import java.util.List;
import java.util.ArrayList;

/**
 * @version $Id $
 */
public class RadionetUserDaoImpl implements UserDetailsService {
    private LocalUserDao dao;
    private UserPermissionDao permissionDao;


    public UserPermissionDao getPermissionDao() {
        return permissionDao;
    }

    public void setPermissionDao(UserPermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public LocalUserDao getDao() {
        return dao;
    }

    public void setDao(LocalUserDao dao) {
        this.dao = dao;
    }

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {
        UserPermission perm = getPermissionDao().read(s);
        if (perm == null){
            throw new UsernameNotFoundException(s);
        }
        List<LocalUser> users = getDao().findByPermission(perm);
        if (users == null || users.isEmpty()){
            throw new UsernameNotFoundException(s);
        }
        LocalUser user = users.iterator().next();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (LocalRole role : user.getRoles()){
            authorities.add(new GrantedAuthorityImpl(role.getRole()));
        }
        GrantedAuthority[] ar = new GrantedAuthority[authorities.size()];
        UserDetails details = new User(s, user.getPermissionEntry().getDigest(), true, true, true, true, authorities.toArray(ar));
        return details;
    }
}
