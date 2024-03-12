package com.microservices.orderservice.utility;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class UrlConstant {
    private static final Object obj;

    static {
        try {
            obj = new JSONParser().parse(new FileReader("src/test/resources/constant.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static final JSONObject jsonObject = (JSONObject) obj;
    public static final String GENERIC_ORDERS_URL = (String) jsonObject.get("genericOrdersUrl");
    public static final String SPECIFIC_ORDER_URL = (String) jsonObject.get("specificOrderUrl");

}
