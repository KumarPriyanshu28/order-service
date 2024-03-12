package com.microservices.orderservice.controller;

import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.ErrorDto;
import com.microservices.orderservice.exception.OrderServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller interface for managing orders.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@RequestMapping("/orders")
public interface OrderController {

    /**
     * Retrieves a list of all orders.
     *
     * @return ResponseEntity containing a list of OrderDto objects if successful.
     * @throws OrderServiceException if there is an issue with the order service.
     */
    @Operation(summary = "Retrieve all orders.",
            description = "Retrieve all the orders from the database.",
            tags = {"GET"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the orders.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))})
    })
    @GetMapping
    ResponseEntity<List<OrderDto>> getAllOrders() throws OrderServiceException;

    /**
     * Creates a new order.
     *
     * @param orderDto The OrderDto object representing the order to be created.
     * @return ResponseEntity containing the created OrderDto if successful.
     */
    @Operation(summary = "Creates a new order.",
            description = "Creates a new order in the database.",
            tags = {"POST"})
    @Parameter(name = "OrderDto", description = "The Dto containing information for creating a new order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully created a new order.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input for creating a order.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    @PostMapping
    CompletableFuture<ResponseEntity<OrderDto>> createOrder(@RequestBody @Valid OrderDto orderDto);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return ResponseEntity containing the OrderDto with the specified ID if successful.
     * @throws OrderServiceException if there is an issue with the order service.
     */
    @Operation(summary = "Retrieves an order by its Id",
            description = "Retrieves a single order from the database based on its unique identifier.",
            tags = {"GET"})
    @Parameter(name = "orderId", description = "The unique identifier of the order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the order.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "order not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    @GetMapping("/{orderId}")
    ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) throws OrderServiceException;

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return ResponseEntity containing the deleted OrderDto if successful.
     * @throws OrderServiceException if there is an issue with the order service.
     */
    @Operation(summary = "Deletes an order by its Id.",
            description = "Deletes an order from the database based on its unique identifier.",
            tags = {"DELETE"})
    @Parameter(name = "orderId", description = "Id of the order to be deleted.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the order.", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(responseCode = "404", description = "order not found.", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDto.class))})
    })
    @DeleteMapping("/{orderId}")
    ResponseEntity<OrderDto> deleteOrderById(@PathVariable Long orderId) throws OrderServiceException;

}

