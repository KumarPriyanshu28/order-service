package com.microservices.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Entity class representing a line in an order.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Data
@Entity
@Table(name = "order_line")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@DynamicInsert
@DynamicUpdate
public class OrderLine {

    /**
     * The unique identifier for the order line.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;

    /**
     * The unique identifier for the product associated with the order line.
     */
    private Long productId;

    /**
     * The quantity of the product in the order line.
     */
    private int quantity;

    /**
     * The Order entity to which the order line belongs.
     */
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    @JsonIgnore
    @ToString.Exclude
    private Order order;

}

