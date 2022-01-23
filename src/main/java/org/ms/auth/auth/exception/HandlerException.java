package org.ms.auth.auth.exception;

import org.ms.auth.auth.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StanderError> userNotFoundException(UserNotFoundException ex,
                                                              HttpServletRequest request){
        StanderError err =
                new StanderError("Not Found", ex.getMessage(),
                        HttpStatus.NOT_FOUND, request.getRequestURI());

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
