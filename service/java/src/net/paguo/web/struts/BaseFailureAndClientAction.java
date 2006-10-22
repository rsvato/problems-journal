package net.paguo.web.struts;

import net.paguo.controller.ClientItemController;

/**
 * User: slava
 * Date: 02.10.2006
 * Time: 0:02:22
 * Version: $Id$
 */
public abstract class BaseFailureAndClientAction extends BaseFailureAction {
    private ClientItemController clientController;

    public ClientItemController getClientController() {
        return clientController;
    }

    public void setClientController(ClientItemController clientController) {
        this.clientController = clientController;
    }
}
