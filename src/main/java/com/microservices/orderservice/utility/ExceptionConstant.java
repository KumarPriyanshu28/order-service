package com.microservices.orderservice.utility;

/**
 * Constants class for defining exception messages used in the OrderService.
 * This class provides a set of static final fields representing common exception messages.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
public class ExceptionConstant {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private  ExceptionConstant() {}

    /**
     * Exception key for indicating that an order list is empty while executing getAllOrders method.
     */
    public static final String GET_ALL_ORDERS_NO_CONTENT = "error.emptyorderlist.getallorders";
    /**
     * Exception key for indicating that an order is unavailable while executing getOrderById method.
     */
    public static final String GET_ORDER_BY_ID_NOT_FOUND = "error.orderunavailable.getorderbyid";
    /**
     * Exception key for indicating that an order is unavailable while executing deleteOrderById method.
     */
    public static final String DELETE_ORDER_BY_ID_NOT_FOUND = "error.orderunavailable.deleteorderbyid";

}
