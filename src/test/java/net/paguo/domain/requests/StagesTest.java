package net.paguo.domain.requests;

import static net.paguo.domain.requests.RequestNextStage.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: sreentenko
 * Date: 22.02.2008
 * Time: 0:48:53
 */
public class StagesTest {

    @Test
    public void testUnacceptableAdvance() {
        final RequestNextStage stage = getNextStage(END_OF_REQUEST, false);
        Assert.assertNull("End state does not have valid next stage", stage);
    }

    @Test
    public void testUnacceptableRewind() {
        final RequestNextStage stage = getPreviousStage(CANCEL_CONFIRM, false);
        Assert.assertNull("Begin state does not have valid next stage", stage);
    }

    @Test
    public void testRewindFromPermanentRequest() {
        final RequestNextStage stage = getPreviousStage(END_OF_REQUEST, true);
        Assert.assertEquals("Permanent request does not have restore cycle", CANCEL_CONFIRM, stage);
    }

    @Test
    public void testRewindFromNonPermanentRequest() {
        final RequestNextStage stage = getPreviousStage(END_OF_REQUEST, false);
        Assert.assertEquals("Non permanent request rewinds to restore cycle", RESTORE_CONFIRM, stage);
    }

}
