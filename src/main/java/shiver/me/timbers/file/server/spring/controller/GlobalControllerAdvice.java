package shiver.me.timbers.file.server.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.server.spring.exception.Error;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * This class provides global controller behaviour e.g. error mapping.
 *
 * @author Karl Bennett
 */
@ControllerAdvice
@ResponseBody
public class GlobalControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public shiver.me.timbers.file.server.spring.exception.Error invalidPath(InvalidPathException e) {

        return new Error(e);
    }
}
