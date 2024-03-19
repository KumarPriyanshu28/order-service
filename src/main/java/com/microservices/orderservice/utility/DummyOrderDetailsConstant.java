package com.microservices.orderservice.utility;

import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.OrderLineDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Constants class for defining order details used in the OrderController.
 * This class provides a set of static final fields representing common order details.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
public class DummyOrderDetailsConstant {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private DummyOrderDetailsConstant() {}

    /**
     * Dummy orderLineDto that is used during service failure.
     */
    public static final OrderLineDto DUMMY_ORDER_LINE_DTO = new OrderLineDto(1001L, 1002L, 500, null);
    /**
     * Dummy orderDto that is used during service failure.
     */
    public static final OrderDto DUMMY_ORDER_DTO = new OrderDto(1000L, LocalDateTime.now(), LocalDateTime.now(), 1000.0, List.of(DUMMY_ORDER_LINE_DTO));
}
