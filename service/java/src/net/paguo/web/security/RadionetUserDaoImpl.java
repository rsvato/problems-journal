package net.paguo.web.security;

import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.springframework.dao.DataAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.paguo.dao.LocalUserDao;
import net.paguo.dao.UserPermissionDao;
import net.paguo.domain.users.LocalUser;
import net.paguo.domain.users.LocalRole;

import java.util.List;
import java.util.ArrayList;

/**
 * @version $Id $
 */
public class RadionetUserDaoImpl implements UserDetailsService {
    private LocalUserDao dao;
    private UserPermissionDao permissionDao;
    private static final Log log = LogFactory.getLog(RadionetUserDaoImpl.class);

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
        LocalUserDao dao = getDao();
        List<LocalUser> perms = dao.findByPermission(s);

        if (perms == null || perms.size() != 1){
            log.error("No permissions found?: " + (perms == null));
            throw new UsernameNotFoundException(s);
        }

        
        LocalUser user = perms.iterator().next();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (LocalRole role : user.getRoles()){
            log.info("User role: " + role.getRole());
            authorities.add(new GrantedAuthorityImpl(role.getRole()));
        }
        GrantedAuthority[] ar = new GrantedAuthority[authorities.size()];
        log.info("All procedures finished. Returning user");
        return new User(s, user.getPermissionEntry().getDigest(), true, true, true, true, authorities.toArray(ar));
    }
}
