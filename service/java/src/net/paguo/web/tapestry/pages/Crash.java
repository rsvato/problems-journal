package net.paguo.web.tapestry.pages;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.valid.ValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;
import net.paguo.domain.problems.NetworkProblem;
import net.paguo.controller.NetworkFailureController;
import net.paguo.controller.exception.ControllerException;
import com.javaforge.tapestry.spring.annotations.InjectSpring;

/**
 * User: slava
 * Date: 07.12.2006
 * Time: 13:40:43
 * Version: $Id$
 */
public abstract class Crash extends BasePage implements PageBeginRenderListener {

    public static final String PAGE_NAME = "Crash";

    @Persist("session")
    public abstract Integer getCrashId();

    public abstract void setCrashId(Integer crashId);

    @Persist("session")
    public abstract NetworkProblem getProblem();

    public abstract void setProblem(NetworkProblem problem);

    @InjectSpring("failureController")
    public abstract NetworkFailureController getController();

    @Bean
    public abstract ValidationDelegate getDelegate();

    public void pageBeginRender(PageEvent event) {
        NetworkProblem problem;
        if (getCrashId() == null) {
            problem = new NetworkProblem();
        } else {
            problem = getController().getNetworkProblem(getCrashId());
        }
        setProblem(problem);
    }

    public String save() {
        if (getDelegate().getHasErrors()){
            return null;
        }
        try {
            getController().saveCrash(getProblem());
        } catch (ControllerException e) {
            getDelegate().record(new ValidatorException(e.getMessage()));
            return null;
        }
        return PAGE_NAME;
    }
}
