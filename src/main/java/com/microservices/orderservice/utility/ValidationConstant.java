package com.microservices.orderservice.utility;

/**
 * Constants class for defining validation messages used in the OrderService.
 * This class provides a set of static final fields representing common validation messages.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
public class ValidationConstant {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private ValidationConstant() {}

    /**
     * Validation message indicating that the order lines must not be empty.
     */
    public static final String ORDER_LINES_NOT_NULL = "{validation.orderLines.notNull}";

    /**
     * Validation message indicating that the order lines must not be empty.
     */
    public static final String ORDER_LINES_NOT_EMPTY = "{validation.orderLines.notEmpty}";

    /**
     * Validation message indicating that the product ID must not be null.
     */
    public static final String PRODUCT_ID_NOT_NULL = "{validation.productId.notNull}";

    /**
     * Validation message indicating that the product ID must be a positive value.
     */
    public static final String PRODUCT_ID_POSITIVE = "{validation.productId.positive}";

    /**
     * Validation message indicating that the quantity must not be null.
     */
    public static final String QUANTITY_NOT_NULL = "{validation.quantity.notNull}";

    /**
     * Validation message indicating that the quantity must be a positive value.
     */
    public static final String QUANTITY_POSITIVE = "{validation.quantity.positive}";

    /**
     * Validation message indicating that the quantity must have a maximum value.
     */
    public static final String QUANTITY_MAXIMUM_VALUE = "{validation.quantity.maximumValue}";
}

