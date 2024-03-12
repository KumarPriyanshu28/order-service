package com.microservices.orderservice.repository;

import com.microservices.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Order entities in the database.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

