package com.microservices.orderservice.utility;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorCodeConstant {
    public static final Integer GET_ALL_ORDERS_ERROR_CODE = Integer.parseInt(PropertiesFileReader
            .getProperties("classpath:errorcode.properties")
            .getProperty("error.emptyorderlist.getallorders"));
    public static final Integer GET_ORDER_BY_ID_ERROR_CODE = Integer.parseInt(PropertiesFileReader
            .getProperties("classpath:errorcode.properties")
            .getProperty("error.orderunavailable.getorderbyid"));
    public static final Integer DELETE_ORDER_BY_ID_ERROR_CODE = Integer.parseInt(PropertiesFileReader
            .getProperties("classpath:errorcode.properties")
            .getProperty("error.orderunavailable.deleteorderbyid"));

}
