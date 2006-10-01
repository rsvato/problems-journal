package net.paguo.web.struts;

import org.apache.struts.action.Action;
import net.paguo.controller.NetworkFailureController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: slava
 * Date: 02.10.2006
 * Time: 0:00:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFailureAction extends Action {
    private NetworkFailureController controller;
    protected static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }
}
