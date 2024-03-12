package com.microservices.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a product.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    /**
     * The unique identifier for the product.
     */
    private long productId;

    /**
     * The name of the product.
     */
    private String productName;

    /**
     * The price of the product.
     */
    private double productPrice;
}

