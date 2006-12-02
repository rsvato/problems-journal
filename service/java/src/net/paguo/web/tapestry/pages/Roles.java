package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InjectPage;

import java.util.Collection;

import net.paguo.controller.UsersController;
import net.paguo.domain.users.LocalRole;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 21.11.2006
 * Time: 0:20:56
 * Version: $Id$
 */
public abstract class Roles extends BasePage {
    public abstract Integer getRoleId();

    @InjectPage("Role")
    public abstract IPage getRolePage();


    @InjectSpring("usersController")
    public abstract UsersController getUsersController();

    public IPage select() {
        Role role = (Role) getRolePage();
        role.setRoleId(getRoleId());
        return role;
    }

    public Collection<LocalRole> getRoles(){
        return getUsersController().getAllRoles();
    }

    
}
