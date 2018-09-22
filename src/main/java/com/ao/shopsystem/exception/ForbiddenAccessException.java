package com.ao.shopsystem.exception;

/**
 * Created by ao on 2018-09-21
 */
public class ForbiddenAccessException extends Exception {

    public ForbiddenAccessException(String message) {
        super(message);
    }

    public ForbiddenAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenAccessException(Throwable cause) {
        super(cause);
    }
}
