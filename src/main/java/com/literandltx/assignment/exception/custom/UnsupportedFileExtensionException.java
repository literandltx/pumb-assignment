package com.literandltx.assignment.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Only CSV and XML files are only supported.")
public class UnsupportedFileExtensionException extends RuntimeException {
    public UnsupportedFileExtensionException(final String message) {
        super(message);
    }

    public UnsupportedFileExtensionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
