package com.microservices.orderservice.utility;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorMessageConstant {
    public static final String GET_ALL_ORDERS_ERROR_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("error.emptyorderlist.getallorders");
    public static final String GET_ORDER_BY_ID_ERROR_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("error.orderunavailable.getorderbyid");
    public static final String DELETE_ORDER_BY_ID_ERROR_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("error.orderunavailable.deleteorderbyid");

}
