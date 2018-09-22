package com.ao.shopsystem;

import com.ao.shopsystem.controller.ItemController;
import com.ao.shopsystem.controller.OrderController;
import com.ao.shopsystem.exception.AlreadyExistsException;
import com.ao.shopsystem.exception.BadRequestException;
import com.ao.shopsystem.exception.ForbiddenAccessException;
import java.util.HashMap;
import java.util.Map;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller Advice for all the controllers.
 * Created by ao on 2018-09-21
 */
@ControllerAdvice(basePackageClasses = {ItemController.class, OrderController.class})
public class WebExceptionHandler {

    private static final String BAD_REQUEST_CODE = "400";
    private static final String FORBIDDEN_ACCESS_CODE = "403";
    private static final String CONFLICT_CODE = "409";
    private static final String NOT_FOUND_CODE = "404";
    private static final String INTERNAL_ERROR_CODE = "500";

    private static final String CODE = "code";
    private static final String MESSAGE = "error";

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public Map<String, String> handleConflict(Exception e) {

        Map<String, String> response = new HashMap<>();

        response.put(CODE, CONFLICT_CODE);
        response.put(MESSAGE, e.getLocalizedMessage());

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Map<String, String> handleBadRequest(Exception e) {

        Map<String, String> response = new HashMap<>();

        response.put(CODE, BAD_REQUEST_CODE);
        response.put(MESSAGE, e.getMessage());

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFound(Exception e) {

        Map<String, String> response = new HashMap<>();

        response.put(CODE, NOT_FOUND_CODE);
        response.put(MESSAGE, e.getMessage());

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalError.class)
    public Map<String, String> handleInternalError(Exception e) {

        Map<String, String> response = new HashMap<>();

        response.put(CODE, INTERNAL_ERROR_CODE);
        response.put(MESSAGE, e.getMessage());

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenAccessException.class)
    public Map<String, String> handleForbiddenAccess(Exception e) {

        Map<String, String> response = new HashMap<>();

        response.put(CODE, FORBIDDEN_ACCESS_CODE);
        response.put(MESSAGE, e.getMessage());

        return response;
    }
}
