package com.microservices.orderservice.config;

import com.microservices.orderservice.service.mapper.OrderLineMapperImpl;
import com.microservices.orderservice.service.mapper.OrderMapperImpl;
import com.microservices.orderservice.service.mapper.OrderLineMapper;
import com.microservices.orderservice.service.mapper.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Configuration class for handling Order-related beans and settings.
 * Includes the configuration for message properties, JPA auditing, and bean definitions for mappers.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@PropertySource("classpath:messages.properties")
public class OrderConfig {

    /**
     * Creates and returns a bean for the OrderMapper interface.
     *
     * @return An implementation of the OrderMapper interface.
     */
    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapperImpl();
    }

    /**
     * Creates and returns a bean for the OrderLineMapper interface.
     *
     * @return An implementation of the OrderLineMapper interface.
     */
    @Bean
    public OrderLineMapper orderLineMapper() {
        return new OrderLineMapperImpl();
    }
}
