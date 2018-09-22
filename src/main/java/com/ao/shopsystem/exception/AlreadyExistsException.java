package com.ao.shopsystem.exception;

/**
 * Created by ao on 2018-09-21
 */
public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}

