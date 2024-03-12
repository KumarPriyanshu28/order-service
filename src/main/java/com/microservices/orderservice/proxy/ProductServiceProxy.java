package com.microservices.orderservice.proxy;

import com.microservices.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for communicating with the PRODUCT-SERVICE.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceProxy {

    /**
     * Retrieves product information by its ID from the PRODUCT-SERVICE.
     *
     * @param productId The unique identifier of the product.
     * @return ResponseEntity containing the ProductDto if the request is successful.
     */
    @GetMapping("products/{productId}")
    ResponseEntity<ProductDto> getProductById(@PathVariable Long productId);
}

