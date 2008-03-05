package net.paguo.controller;

import net.paguo.controller.exception.ControllerException;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.RequestInformation;
import net.paguo.domain.requests.RequestNextStage;
import static net.paguo.domain.requests.RequestNextStage.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: sreentenko
 * Date: 22.02.2008
 * Time: 1:26:01
 */
public class TestChangeRequestLifecycle {

    @Test
    public void testAdvancesNP() throws ControllerException {
        ChangeStatusRequest request = new ChangeStatusRequest();
        request.setPermanent(false);

        request.setNextStage(CANCEL_CONFIRM);
        request.setCancelRequest(new RequestInformation());
        ChangeStatusRequestController controller = new ChangeStatusRequestController();

        controller.advanceRequest(request, new RequestInformation());
        Assert.assertEquals("1", RequestNextStage.RESTORE_REQUEST, request.getNextStage());
        Assert.assertNotNull(request.getCancelExec());

        controller.advanceRequest(request, new RequestInformation());
        Assert.assertEquals("2", RequestNextStage.RESTORE_CONFIRM, request.getNextStage());
        Assert.assertNotNull(request.getRestoreRequest());

        controller.advanceRequest(request, new RequestInformation());
        Assert.assertEquals("3", RequestNextStage.END_OF_REQUEST, request.getNextStage());
        Assert.assertNotNull(request.getRestoreExec());

        try {
            controller.advanceRequest(request, new RequestInformation());
            Assert.fail("No advance after last stage");
        } catch (ControllerException e) {
            Assert.assertEquals(ControllerException.Cause.LOGIC, e.getExceptionCause());
        }
    }

    @Test
    public void testAdvancesP() throws ControllerException {
        ChangeStatusRequest request = new ChangeStatusRequest();
        request.setPermanent(true);

        request.setNextStage(CANCEL_CONFIRM);
        request.setCancelRequest(new RequestInformation());
        ChangeStatusRequestController controller = new ChangeStatusRequestController();

        controller.advanceRequest(request, new RequestInformation());
        Assert.assertEquals("END OF GAME SHOULD BE HERE", RequestNextStage.END_OF_REQUEST, request.getNextStage());
        Assert.assertNotNull("Cancel exec info should be set", request.getCancelExec());

        try {
            controller.advanceRequest(request, new RequestInformation());
            Assert.fail("No advance after last stage");
        } catch (ControllerException e) {
            Assert.assertEquals(ControllerException.Cause.LOGIC, e.getExceptionCause());
        }
    }

    @Test
    public void testRewindRequestNP() throws ControllerException {
        ChangeStatusRequest request = new ChangeStatusRequest();
        request.setPermanent(false);

        request.setNextStage(END_OF_REQUEST);
        request.setCancelRequest(new RequestInformation());
        request.setCancelExec(new RequestInformation());
        request.setRestoreRequest(new RequestInformation());
        request.setRestoreExec(new RequestInformation());
        ChangeStatusRequestController controller = new ChangeStatusRequestController();

        controller.rewindRequest(request);
        Assert.assertNull("Restore exec should be null after first rewind", request.getRestoreExec());
        Assert.assertEquals("Restore exec", RESTORE_CONFIRM, request.getNextStage());

        controller.rewindRequest(request);
        Assert.assertNull("Restore exec should be null after first rewind", request.getRestoreRequest());
        Assert.assertEquals("Restore exec", RESTORE_REQUEST, request.getNextStage());

        controller.rewindRequest(request);
        Assert.assertNull("Restore exec should be null after first rewind", request.getCancelExec());
        Assert.assertEquals("Restore exec", CANCEL_CONFIRM, request.getNextStage());

        try {
            controller.rewindRequest(request);
            Assert.fail("Request should not be rewinded from initial state");
        } catch (ControllerException e) {
            Assert.assertEquals(ControllerException.Cause.LOGIC, e.getExceptionCause());
        }
    }


    @Test
    public void testRewindRequestP() throws ControllerException {
        ChangeStatusRequest request = new ChangeStatusRequest();
        request.setPermanent(true);

        request.setNextStage(END_OF_REQUEST);
        request.setCancelRequest(new RequestInformation());
        request.setCancelExec(new RequestInformation());
        ChangeStatusRequestController controller = new ChangeStatusRequestController();

        controller.rewindRequest(request);
        Assert.assertNull("Cancel exec should be null after first rewind", request.getCancelExec());
        Assert.assertEquals("Cancel confirm as a first stage", CANCEL_CONFIRM, request.getNextStage());


        try {
            controller.rewindRequest(request);
            Assert.fail("Request should not be rewinded from initial state");
        } catch (ControllerException e) {
            Assert.assertEquals(ControllerException.Cause.LOGIC, e.getExceptionCause());
        }

        controller.advanceRequest(request, new RequestInformation());
        Assert.assertNotNull(request.getCancelExec());
        Assert.assertNull("Should not exist with perm req", request.getRestoreRequest());
        Assert.assertNull("Should not exist with perm req", request.getRestoreExec());
        Assert.assertEquals(END_OF_REQUEST, request.getNextStage());
    }

}
