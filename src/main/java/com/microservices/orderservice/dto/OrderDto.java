package com.microservices.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.microservices.orderservice.utility.ValidationConstant.ORDER_LINES_NOT_EMPTY;
import static com.microservices.orderservice.utility.ValidationConstant.ORDER_LINES_NOT_NULL;

/**
 * Data Transfer Object (DTO) representing an order.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    /**
     * The unique identifier for the order.
     */
    private long orderId;

    /**
     * The date and time when the order was created.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;

    /**
     * The date and time when the order was last modified.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime modifiedDate;

    /**
     * The total price of the order.
     */
    private double totalPrice;

    /**
     * The list of order lines associated with the order.
     */
    @Valid
    @NotNull(message = ORDER_LINES_NOT_NULL)
    @NotEmpty(message = ORDER_LINES_NOT_EMPTY)
    private List<OrderLineDto> orderLineDtoList;
}

