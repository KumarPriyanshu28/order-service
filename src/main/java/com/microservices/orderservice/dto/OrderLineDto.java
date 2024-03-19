package com.microservices.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.microservices.orderservice.utility.ValidationConstant.*;

/**
 * Data Transfer Object (DTO) representing an order line.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDto {

    /**
     * The unique identifier for the order line.
     */
    private Long orderLineId;

    /**
     * The unique identifier for the product associated with the order line.
     */
    @NotNull(message = PRODUCT_ID_NOT_NULL)
    @Positive(message = PRODUCT_ID_POSITIVE)
    private Long productId;

    /**
     * The quantity of the product in the order line.
     */
    @NotNull(message = QUANTITY_NOT_NULL)
    @Positive(message = QUANTITY_POSITIVE)
    @Max(value = 1000, message = QUANTITY_MAXIMUM_VALUE)
    private Integer quantity;

    /**
     * The OrderDto object representing the order to which the order line belongs.
     */
    @JsonIgnore
    private OrderDto orderDto;
}

