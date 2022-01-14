package com.volodymyrkozlov.filemanagement.app.exception;

import com.volodymyrkozlov.filemanagement.app.dto.web.response.error.ResponseError;
import com.volodymyrkozlov.filemanagement.app.dto.web.response.error.ResponseErrorDetails;
import com.volodymyrkozlov.filemanagement.app.dto.web.response.error.ResponseErrorStatus;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handle(final ConstraintViolationException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new ResponseError(toErrorStatus(status), toResponseErrorDetails(ex)));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseError> handle(final BindException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new ResponseError(toErrorStatus(status), toResponseErrorDetails(ex)));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> handle(final EntityNotFoundException ex) {
        final var status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status)
            .body(new ResponseError(toErrorStatus(status), toResponseErrorDetails(ex)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handle(final MethodArgumentNotValidException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new ResponseError(toErrorStatus(status), toResponseErrorDetails(ex)));
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ResponseError> handle(final PropertyReferenceException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new ResponseError(toErrorStatus(status), toResponseErrorDetails(ex)));
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ResponseError> handle(final InvalidArgumentException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new ResponseError(toErrorStatus(status), toResponseErrorDetails(ex)));
    }

    private static List<ResponseErrorDetails> toResponseErrorDetails(final ConstraintViolationException ex) {
        return ex.getConstraintViolations()
            .stream()
            .map(constraintViolation -> new ResponseErrorDetails(
                constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString()))
            .collect(Collectors.toList());
    }

    private static List<ResponseErrorDetails> toResponseErrorDetails(final InvalidArgumentException ex) {
        return Collections.singletonList(new ResponseErrorDetails(ex.getMessage(), null));
    }

    private static List<ResponseErrorDetails> toResponseErrorDetails(final PropertyReferenceException ex) {
        return Collections.singletonList(new ResponseErrorDetails(ex.getMessage(), ex.getPropertyName()));
    }

    private static List<ResponseErrorDetails> toResponseErrorDetails(final EntityNotFoundException ex) {
        return Collections.singletonList(new ResponseErrorDetails(ex.getMessage(), null));
    }

    private static ResponseErrorStatus toErrorStatus(final HttpStatus httpStatus) {
        return new ResponseErrorStatus(httpStatus.value(), httpStatus.getReasonPhrase());
    }

    public static List<ResponseErrorDetails> toResponseErrorDetails(final BindingResult bindingResult) {
        final var errors = new ArrayList<ResponseErrorDetails>();
        bindingResult.getFieldErrors()
            .forEach(e -> errors.add(new ResponseErrorDetails(e.getDefaultMessage(), e.getField())));
        bindingResult.getGlobalErrors()
            .forEach(e -> errors.add(new ResponseErrorDetails(e.getDefaultMessage(), e.getObjectName())));

        return errors;
    }
}
