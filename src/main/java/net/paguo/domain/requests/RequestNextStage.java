package net.paguo.domain.requests;

/**
 * @author Reyentenko
 */
public enum RequestNextStage {
    CANCEL_REQUEST,   // not used and should not be used. It's preinitial state.
    CANCEL_CONFIRM,
    RESTORE_REQUEST,
    RESTORE_CONFIRM,
    END_OF_REQUEST;

    /**
     * Calculates next request stage. Null return value means that there is no
     * correct next stage (e.g., current is end)
     *
     * @param stage       current request stage
     * @param isPermanent if request is permanent
     * @return valid next stage or null when request advance is impossible
     */
    public static RequestNextStage getNextStage(RequestNextStage stage, boolean isPermanent) {
        if (stage == END_OF_REQUEST) {
            return null;
        }

        final int i = stage.ordinal();
        RequestNextStage next = values()[i + 1];
        if (next.ordinal() > CANCEL_CONFIRM.ordinal() && isPermanent) {
            return END_OF_REQUEST;
        }
        return next;
    }

    /**
     * Calculates previous request stage for rewinding of request to a step back. Null return
     * value means that there is no a valid previous state to rewind to.
     *
     * @param stage       current stage
     * @param isPermanent means permanent request (no restore phases)
     * @return valid previous stage or null when rewind is impossible
     */
    public static RequestNextStage getPreviousStage(RequestNextStage stage, boolean isPermanent) {
        if (stage == CANCEL_CONFIRM || stage == CANCEL_REQUEST) {
            //we don't want to go to preinitial state
            return null;
        }

        if (stage == END_OF_REQUEST && isPermanent) {
            return CANCEL_CONFIRM;
        }

        return values()[stage.ordinal() - 1];
    }


}
