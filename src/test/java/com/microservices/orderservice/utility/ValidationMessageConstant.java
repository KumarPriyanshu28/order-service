package com.microservices.orderservice.utility;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationMessageConstant {
    public static final String ORDER_LINES_NOT_NULL_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.orderLines.notNull");
    public static final String ORDER_LINES_NOT_EMPTY_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.orderLines.notEmpty");
    public static final String PRODUCT_ID_NOT_NULL_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.productId.notNull");
    public static final String PRODUCT_ID_POSITIVE_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.productId.positive");
    public static final String QUANTITY_NOT_NULL_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.quantity.notNull");
    public static final String QUANTITY_POSITIVE_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.quantity.positive");
    public static final String QUANTITY_MAXIMUM_VALUE_MESSAGE = PropertiesFileReader
            .getProperties("classpath:messages.properties")
            .getProperty("validation.quantity.maximumValue");

}
