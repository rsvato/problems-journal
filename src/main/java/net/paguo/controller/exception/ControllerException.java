package net.paguo.controller.exception;

/**
 * User: slava
 * Date: 16.11.2006
 * Time: 0:50:29
 * Version: $Id$
 */
public class ControllerException extends Exception {
    private String message;
    private Cause exceptionCause;

    public enum Cause {
        DATA, LOGIC
    }

    public ControllerException(Throwable t) {
        super(t);
        this.message = t.getMessage();
        exceptionCause = Cause.DATA;
    }

    public ControllerException(String t) {
        super(t);
        exceptionCause = Cause.LOGIC;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cause getExceptionCause() {
        return exceptionCause;
    }

    public void setExceptionCause(Cause cause) {
        this.exceptionCause = cause;
    }
}
