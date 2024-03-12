package com.microservices.orderservice.controller.advice;

import com.microservices.orderservice.dto.ErrorDto;
import com.microservices.orderservice.exception.OrderServiceException;
import com.microservices.orderservice.utility.PropertiesFileReader;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for the Product module.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@RequiredArgsConstructor
@ControllerAdvice(value = "com.microservices.orderservice.controller")
public class OrderServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    /**
     * Handles exceptions specific to the OrderService.
     *
     * @param ex The OrderServiceException.
     * @return ResponseEntity containing error details.
     */
    @ExceptionHandler(OrderServiceException.class)
    public final ResponseEntity<ErrorDto> handleOrderServiceException(final OrderServiceException ex) {
                ErrorDto errorDetails = new ErrorDto(
                Integer.parseInt(PropertiesFileReader.getProperties("classpath:errorcode.properties")
                                                     .getProperty(ex.getMessage())),
                PropertiesFileReader.getProperties("classpath:messages.properties")
                                    .getProperty(ex.getMessage()),
                LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, ex.getHttpStatus());
    }

    /**
     * Handles general exceptions.
     *
     * @param ex The Exception.
     * @return ResponseEntity containing error details.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDto> handleAllExceptions(final Exception ex) {
        ErrorDto errorDetails = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                             ex.getMessage(),
                                             LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation errors.
     *
     * @param ex      The MethodArgumentNotValidException.
     * @param headers The HttpHeaders for the response.
     * @param status  The HttpStatus for the response.
     * @param request The WebRequest.
     * @return ResponseEntity containing a list of error details.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatusCode status,
                                                                  @NonNull final WebRequest request) {
        List<ErrorDto> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDto errorDetails = new ErrorDto(status.value(),
                                                         error.getDefaultMessage(),
                                                         LocalDateTime.now());
                    errors.add(errorDetails);
                });
        return new ResponseEntity<>(errors, headers, status);
    }

    /**
     * Handles internal exceptions.
     *
     * @param ex      The Exception.
     * @param body    The body of the response.
     * @param headers The HttpHeaders for the response.
     * @param status  The HttpStatus for the response.
     * @param request The WebRequest.
     * @return ResponseEntity containing error details.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex,
                                                             @Nullable final Object body,
                                                             @NonNull final HttpHeaders headers,
                                                             @NotNull final HttpStatusCode status,
                                                             @NonNull final WebRequest request) {
        ErrorDto errorDetails = new ErrorDto(status.value(),
                                             ex.getMessage(),
                                             LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, headers, status);
    }

}

