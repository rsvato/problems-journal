package net.paguo.web.struts;

import org.apache.struts.action.Action;
import net.paguo.controller.NetworkFailureController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * User: slava
 * Date: 02.10.2006
 * Time: 0:00:46
 * Version: $Id$
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

    protected static final String NEXT = "next";
    protected static final String ERROR = "error";
}
