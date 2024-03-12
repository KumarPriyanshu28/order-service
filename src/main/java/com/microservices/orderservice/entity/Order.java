package com.microservices.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing an order in the system.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order_tbl")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@DynamicInsert
@DynamicUpdate
public class Order {

    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    /**
     * The date and time when the order was created.
     */
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * The date and time when the order was last modified.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    /**
     * The total price of the order.
     */
    private double totalPrice;

    /**
     * The list of order lines associated with the order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLineList;

}

