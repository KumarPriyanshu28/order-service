package com.microservices.orderservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class representing an exception in the order service.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Getter
public class OrderServiceException extends RuntimeException {

    /**
     * The HTTP status associated with the exception.
     */
    private final HttpStatus httpStatus;

    /**
     * Constructs a new OrderServiceException with the specified message and HTTP status.
     *
     * @param message    The detail message explaining the reason for the exception.
     * @param httpStatus The HTTP status associated with the exception.
     */
    public OrderServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}

