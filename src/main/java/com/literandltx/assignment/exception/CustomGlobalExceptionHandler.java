package com.literandltx.assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Default Global Exception Handler
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(
            final Exception exception,
            final WebRequest request
    ) {
        final ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getLocalizedMessage(),
                "Unexpected error occurred."
        );

        log.info(String.format("Exception: %s, was handled.", exception.getMessage()));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}