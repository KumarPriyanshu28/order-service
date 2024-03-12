package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderDto;

import java.util.List;

/**
 * Service interface for managing orders.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
public interface OrderService {

    /**
     * Retrieves a list of all orders.
     *
     * @return List of OrderDto objects representing all orders.
     */
    List<OrderDto> getAllOrders();

    /**
     * Creates a new order.
     *
     * @param orderDto The OrderDto object representing the order to be created.
     * @return The created OrderDto.
     */
    OrderDto createOrder(OrderDto orderDto);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The OrderDto with the specified ID.
     */
    OrderDto getOrderById(Long orderId);

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return The deleted OrderDto.
     */
    OrderDto deleteOrderById(Long orderId);
}

