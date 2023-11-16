package org.bogdanovtimm.t1consalting.exception;

/**
 * Custom {@code RuntimeException} for errors that might occur
 * during hadnling request from client
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
