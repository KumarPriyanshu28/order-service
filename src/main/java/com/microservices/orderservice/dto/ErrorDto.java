package com.microservices.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing an error response.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    /**
     * The HTTP status code associated with the error.
     */
    private Integer statusCode;

    /**
     * A human-readable message describing the error.
     */
    private String message;

    /**
     * The timestamp indicating when the error occurred.
     */
    private LocalDateTime timestamp;
}


