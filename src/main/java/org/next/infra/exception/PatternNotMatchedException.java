package org.next.infra.exception;

public class PatternNotMatchedException extends RuntimeException {

    public PatternNotMatchedException(String message) {
        super(message);
    }
}
