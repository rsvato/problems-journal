package net.paguo.generic.dao;

import net.paguo.dao.LocalUserDao;
import net.paguo.dao.UserPermissionDao;
import net.paguo.dao.LocalRoleDao;
import net.paguo.domain.users.LocalRole;
import net.paguo.domain.users.UserPermission;
import net.paguo.domain.users.LocalUser;

import java.util.Set;
import java.util.HashSet;

/**
 * @version $Id $
 */
public class LocalUserTest extends AbstractDAOTest {
    public void testCreate(){
        Set<LocalRole> roles = new HashSet<LocalRole>();
        LocalRole role = new LocalRole();
        role.setRole("ROLE_ANY");
        role.setRoleDescription("Generic role for all users");

        LocalRole otherRole = new LocalRole();
        otherRole.setRole("ROLE_SUPERVISOR");
        otherRole.setRoleDescription("Supervisor role");

        getRoleDao().create(role);
        getRoleDao().create(otherRole);

        roles.add(role);
        roles.add(otherRole);

        String permId = "slava";
        UserPermission permission = getPermissionDao().read(permId);
        assertNotNull(permission);
        LocalUser user = new LocalUser();
        user.setName("Svyatoslav");
        user.setFamilyName("Reyentenko");
        user.setPermissionEntry(permission);
        user.setRoles(roles);
        getUserDao().create(user);
    }

    @SuppressWarnings("unchecked")
    private LocalUserDao getUserDao(){
        return (LocalUserDao) ctx.getBean("userDao");
    }

    @SuppressWarnings("unchecked")
    private UserPermissionDao getPermissionDao(){
        return (UserPermissionDao) ctx.getBean("permissionDao");
    }

    private LocalRoleDao getRoleDao(){
        return (LocalRoleDao) ctx.getBean("roleDao");
    }
}
