package com.microservices.orderservice.controller.impl;

import com.microservices.orderservice.controller.OrderController;
import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.OrderLineDto;
import com.microservices.orderservice.service.OrderService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.microservices.orderservice.utility.DummyOrderDetailsConstant.DUMMY_ORDER_DTO;

/**
 * Implementation of the {@link OrderController} interface that handles HTTP requests related to orders.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    /**
     * Retrieves a list of all orders.
     *
     * @return ResponseEntity containing a list of OrderDto objects if successful.
     */
    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    /**
     * Creates a new order.
     *
     * @param orderDto The OrderDto object representing the order to be created.
     * @return ResponseEntity containing the created OrderDto if successful.
     */
    @Override
    @Retry(name = "retryProductService", fallbackMethod = "retryProductServiceFallback")
    @CircuitBreaker(name = "orderCircuitBreaker", fallbackMethod = "orderCircuitBreakerFallback")
    @RateLimiter(name = "orderRateLimiter", fallbackMethod = "orderRateLimiterFallback")
    @TimeLimiter(name = "orderTimeLimiter", fallbackMethod = "orderTimeLimiterFallback")
    @Bulkhead(name = "orderBulkHead", fallbackMethod = "orderBulkHeadFallback")
    public CompletableFuture<ResponseEntity<OrderDto>> createOrder(OrderDto orderDto) {
        return CompletableFuture.supplyAsync(() -> orderService.createOrder(orderDto))
                .thenApply(order -> ResponseEntity.status(HttpStatus.CREATED).body(order));
    }

    public ResponseEntity<OrderDto> orderCircuitBreakerFallback(Exception ex) {
        log.info("product-service is down due to {}", ex.getMessage());
        return new ResponseEntity<>(DUMMY_ORDER_DTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<OrderDto> retryProductServiceFallback(Exception ex) {
        log.info("maximum retry limit has been reached due to {}", ex.getMessage());
        return new ResponseEntity<>(DUMMY_ORDER_DTO, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<OrderDto> orderBulkHeadFallback(Exception ex) {
        log.info("order-service does not permit further calls due to {}", ex.getMessage());
        return new ResponseEntity<>(DUMMY_ORDER_DTO, HttpStatus.GATEWAY_TIMEOUT);
    }

    public ResponseEntity<OrderDto> orderRateLimiterFallback(Exception ex) {
        log.info("order-service does not permit further calls due to {}", ex.getMessage());
        return new ResponseEntity<>(DUMMY_ORDER_DTO, HttpStatus.TOO_MANY_REQUESTS);
    }

    public CompletableFuture<ResponseEntity<OrderDto>> orderTimeLimiterFallback(Exception ex) {
        log.info("order-service does not permit further calls in this time interval due to {}", ex.getMessage());
        OrderLineDto orderLineDto = new OrderLineDto(1001L, 1002L, 500, null);
        OrderDto orderDto = new OrderDto(1000L, LocalDateTime.now(), LocalDateTime.now(), 1000.0, List.of(orderLineDto));
        return CompletableFuture.completedFuture(new ResponseEntity<>(orderDto, HttpStatus.REQUEST_TIMEOUT));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return ResponseEntity containing the OrderDto with the specified ID if successful.
     */
    @Override
    public ResponseEntity<OrderDto> getOrderById(Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return ResponseEntity containing the deleted OrderDto if successful.
     */
    @Override
    public ResponseEntity<OrderDto> deleteOrderById(Long orderId) {
        return new ResponseEntity<>(orderService.deleteOrderById(orderId), HttpStatus.OK);
    }

}
