package org.next.infra.user.controller;

public class ErrorResponse extends RuntimeException {

    public static final String DEFAULT_ERROR_MESSAGE = "ERROR.";

    public ErrorResponse() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public ErrorResponse(String errorMessage) {
        super(errorMessage);
    }

    public ErrorResponse(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
