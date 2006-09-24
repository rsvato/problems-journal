package net.paguo.generic.dao;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.clients.ClientItem;
import net.paguo.dao.ClientItemDAO;

import java.util.Date;
import java.util.List;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 25.08.2006 0:54:01
 */
public class FailureControllerTest extends AbstractDAOTest{
    private NetworkFailureController getController(){
        return (NetworkFailureController) getCtx().getBean("failureController");
    }

    private ClientItemDAO getClientDao(){
        return (net.paguo.dao.ClientItemDAO) ctx.getBean("clientItemDao");
    }

    public void testCreateFailure(){
        String cause = "Clientless failure";
        Date time = new Date();
        getController().createFailure(cause, time);
    }

    public void testCreateClientFailure(){
        ClientItem clientItem = getClientDao().read(1);
        if (clientItem == null){
            fail("There must be a first client");
        }
        getController().createComplaint("Clientful failure", new Date(), clientItem);
    }

    public void testGetFailure(){
        ClientItem clientItem = getClientDao().read(1);
        if (clientItem == null){
            fail("There must be a first client");
        }
        List failures = getController().findByClient(clientItem);
        assertTrue(failures.size() > 0);
    }

    public void testFindOpen(){
        List failures = getController().findOpen();
        assertTrue(failures.size() > 0);
    }
}
