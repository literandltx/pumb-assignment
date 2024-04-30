package com.literandltx.assignment.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occurred while parsing the file.")
public class FileParseException extends RuntimeException {
    public FileParseException(final String message) {
        super(message);
    }

    public FileParseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
