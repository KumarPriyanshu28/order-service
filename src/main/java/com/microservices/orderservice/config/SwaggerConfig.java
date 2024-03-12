package com.microservices.orderservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation settings in the order-service.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title="order-service",
                version = "1.0.0",
                contact = @Contact(
                        name = "Kumar Priyanshu",
                        email = "kumarpriyanshu2822@gmail.com"),
                license = @License(
                        name = "Terms of Service",
                        url = "")),
        servers= @Server(url = "localhost:8086/orders")
)
public class SwaggerConfig {

}
