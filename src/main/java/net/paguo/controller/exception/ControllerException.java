package net.paguo.controller.exception;

/**
 * User: slava
 * Date: 16.11.2006
 * Time: 0:50:29
 * Version: $Id$
 */
public class ControllerException extends Exception {
    private String message;
    public ControllerException(Throwable t) {
        super(t);
        this.message = t.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
