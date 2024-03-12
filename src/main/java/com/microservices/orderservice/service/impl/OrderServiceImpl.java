package com.microservices.orderservice.service.impl;

import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.OrderLineDto;
import com.microservices.orderservice.dto.ProductDto;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.entity.OrderLine;
import com.microservices.orderservice.exception.OrderServiceException;
import com.microservices.orderservice.proxy.ProductServiceProxy;
import com.microservices.orderservice.repository.OrderRepository;
import com.microservices.orderservice.service.OrderService;
import com.microservices.orderservice.service.mapper.OrderLineMapper;
import com.microservices.orderservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link OrderService} interface providing business logic for order operations.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderLineMapper orderLineMapper;
    private final OrderRepository orderRepository;
    private final ProductServiceProxy productServiceProxy;

    /**
     * Retrieves a list of all orders.
     *
     * @return List of OrderDto objects representing all orders.
     * @throws OrderServiceException if no orders are available.
     */
    @Override
    public List<OrderDto> getAllOrders() {
        log.debug("Entering in OrderServiceImpl : getAllOrders()");
        log.info("Getting all orders");
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            log.error(GET_ALL_ORDERS_NO_CONTENT);
            throw new OrderServiceException(GET_ALL_ORDERS_NO_CONTENT, HttpStatus.NO_CONTENT);
        }
        log.debug("Retrieved list of orders : {}", orderMapper.orderListToOrderDtoList(orderList));
        log.debug("Exiting from OrderServiceImpl : getAllOrders()");
        return orderMapper.orderListToOrderDtoList(orderList);
    }

    /**
     * Creates a new order.
     *
     * @param orderDto The OrderDto object representing the order to be created.
     * @return The created OrderDto.
     * @throws OrderServiceException if a product is not available or an order cannot be created.
     */
    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.debug("Entering in OrderServiceImpl : createOrder()");
        log.info("Creating order: {}", orderDto);
        double totalPrice = 0;
        int quantity;
        List<OrderLineDto> orderLineDtoList = orderDto.getOrderLineDtoList();
        for (OrderLineDto orderLineDto : orderLineDtoList) {
            ProductDto productDto = productServiceProxy.getProductById(orderLineDto.getProductId()).getBody();
            log.info("product-service called");
            quantity = orderLineDto.getQuantity();
            totalPrice += (productDto != null ? productDto.getProductPrice() : 0) * quantity;
        }
        orderDto.setTotalPrice(totalPrice);
        Order order = orderMapper.orderDtoToOrder(orderDto);
        List<OrderLine> orderLineList = orderLineMapper.orderLineDtoListToOrderLineList(orderLineDtoList);
        for (OrderLine orderLine : orderLineList) {
            orderLine.setOrder(order);
        }
        order.setOrderLineList(orderLineList);
        Order savedOrder = orderRepository.save(order);
        log.debug("Created order : {}", orderMapper.orderToOrderDto(savedOrder));
        log.debug("Exiting from OrderServiceImpl : createOrder()");
        return orderMapper.orderToOrderDto(savedOrder);
    }



    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The OrderDto with the specified ID.
     * @throws OrderServiceException if the order is not found.
     */
    @Override
    public OrderDto getOrderById(Long orderId) {
        log.debug("Entering in OrderServiceImpl : getOrderById()");
        log.info("Getting order by id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException(GET_ORDER_BY_ID_NOT_FOUND, HttpStatus.NOT_FOUND));
        log.debug("Retrieved order : {}", orderMapper.orderToOrderDto(order));
        log.debug("Exiting from OrderServiceImpl : getOrderById()");
        return orderMapper.orderToOrderDto(order);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return The deleted OrderDto.
     * @throws OrderServiceException if the order is not found.
     */
    @Override
    public OrderDto deleteOrderById(Long orderId) {
        log.debug("Entering in OrderServiceImpl : deleteOrderById()");
        log.info("Deleting order by id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException(DELETE_ORDER_BY_ID_NOT_FOUND, HttpStatus.NOT_FOUND));
        orderRepository.deleteById(orderId);
        log.debug("Deleted order : {}", orderMapper.orderToOrderDto(order));
        log.debug("Exiting from OrderServiceImpl : deleteOrderById()");
        return orderMapper.orderToOrderDto(order);
    }

}
