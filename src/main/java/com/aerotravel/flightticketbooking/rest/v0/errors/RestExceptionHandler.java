package com.aerotravel.flightticketbooking.rest.v0.errors;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Something went wrong upon handling the request: {}", request, ex);
        var error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Server Error", findAllCauses(ex));
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("Something was not valid upon handling the request: {}", request, ex);
        var details = getDetails(ex.getBindingResult());
        var error = new ErrorResponse(HttpStatus.BAD_REQUEST + " - BAD, BAD REQUEST",
                "Validation Failed", details);
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        log.error("Entity was not found upon handling the request: {}", request, ex);
        var details = List.of(ex.getLocalizedMessage());
        var error = new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
                "Entity not found.", details);
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, HttpMessageConversionException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        log.error("Something was violated upon handling the request: {}", request, ex);
        var error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), findAllCauses(ex));
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> onConstraintValidationException(ConstraintViolationException ex) {
        log.error("Something was violated.", ex);
        final List<String> violations = ex.getConstraintViolations().stream()
                .map(
                        violation -> String.format("Not a valid (%s) value for %s field. %s",
                                violation.getInvalidValue(),
                                violation.getPropertyPath().toString(),
                                violation.getMessage())
                )
                .collect(Collectors.toList());

        var error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), violations);

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected List<String> findAllCauses(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        List<String> result = new ArrayList<>();
        result.add(throwable.getLocalizedMessage());
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
            result.add(rootCause.getLocalizedMessage());
        }

        return result;
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Something was not binded right upon handling the request: {}", request, ex);
        var details = getDetails(ex.getBindingResult());
        var error = new ErrorResponse(status.toString(), ex.getMessage(), details);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private List<String> getDetails(BindingResult ex) {
        List<String> details = new ArrayList<>();
        ex.getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            details.add(fieldName + " | " + errorMessage);
        });
        return details;
    }
}
