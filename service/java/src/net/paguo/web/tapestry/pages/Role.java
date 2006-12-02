package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.IPage;
import org.apache.tapestry.valid.ValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;
import net.paguo.domain.users.LocalRole;
import net.paguo.controller.UsersController;
import net.paguo.controller.exception.ControllerException;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 21.11.2006
 * Time: 0:22:57
 * Version: $Id$
 */
public abstract class Role extends BasePage implements PageBeginRenderListener {

    @Persist("role")
    public abstract Integer getRoleId();
    public abstract void setRoleId(Integer id);

    public abstract LocalRole getSelectedRole();
    public abstract void setSelectedRole(LocalRole role);

    @Persist("role")
    public abstract LocalRole getOldRole();
    public abstract void setOldRole(LocalRole role);

    @InjectSpring("usersController")
    public abstract UsersController getUsersController();

    @Bean
    public abstract ValidationDelegate getDelegate();

    public void pageBeginRender(PageEvent event) {
        LocalRole role;
        if (getRoleId() == null){
            role = new LocalRole();
        }else{
            role = getUsersController().readRole(getRoleId());
            if (! event.getRequestCycle().isRewinding()){
                setOldRole(getUsersController().readRole(getRoleId()));
            }else{
                if (! getOldRole().equals(role)){
                    throw new PageRedirectException("Roles");
                }
            }
        }
        setSelectedRole(role);
    }


    public IPage onSave(){
        ValidationDelegate delegate = getDelegate();
        if (delegate.getHasErrors()){
            return null;
        }
        LocalRole role = getSelectedRole();
        try{
            getUsersController().saveRole(role);
        } catch (ControllerException e) {
            delegate.record(new ValidatorException(e.getMessage()));
            throw new PageRedirectException(getPageName());
        }

        return getRolesPage();
    }

    @InjectPage("Roles")
    public abstract IPage getRolesPage();
}
