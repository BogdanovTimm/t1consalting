package org.bogdanovtimm.t1consalting.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.bogdanovtimm.t1consalting.response.HttpResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom Exception Handler
 */
@RestControllerAdvice
@Slf4j
public class CharCounterExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles Exceptions for arguments with {@code @Valid} annotation.
     * Sends {@code ResponseEntity} with detailed explanation about Validation violations.
     * 
     * @param exception - {@code MethodArgumentNotValidException}
     * @param headers - {@code HttpHeaders}
     * @param statusCode - {@code HttpStatusCode}
     * @param request - {@code WebRequest}
     * @return ResponseEntity with all violations of validation
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request) {
        log.error(exception.getMessage());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fieldMessage = fieldErrors.stream()
                                         .map(FieldError::getDefaultMessage)
                                         .collect(Collectors.joining(", "));
        return new ResponseEntity<>(HttpResponse.builder()
                                                .timeStamp(LocalDateTime.now().toString())
                                                .statusCode(statusCode.value())
                                                .status(HttpStatus.resolve(statusCode.value()))
                                                .exceptionReason(fieldMessage)
                                                //.developerMessage(exception.getMessage()) // Only for debugging purposes
                                                .build(),
                                    statusCode);
    }

    /**
     * {@code ExceptionHandler} for {@code ApiException}
     * 
     * @param exception - any {@code Exception}
     * @return {@code ResponseEntity} with detailed explanation about an error:
     * {@code Bad Request} with code {@code 400} and error message.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<HttpResponse> apiException(ApiException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                                    HttpResponse.builder()
                                                .timeStamp(LocalDateTime.now().toString())
                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                .status(HttpStatus.BAD_REQUEST)
                                                .exceptionReason(exception.getMessage())
                                                //.developerMessage(exception.getMessage()) // Only for debugging purposes
                                                .build(),
                                    HttpStatus.BAD_REQUEST);
    }

    /**
     * Default {@code ExceptionHandler}
     * 
     * @param exception - any {@code Exception}
     * @return {@code ResponseEntity} with detailed explanation about an error:
     * {@code Internal Server Error} with code {@code 500} and error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> exception(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                                    HttpResponse.builder()
                                                .timeStamp(LocalDateTime.now().toString())
                                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                .exceptionReason("Unexpected exception")
                                                //.developerMessage(exception.getMessage()) // Only for debugging purposes
                                                .build(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
