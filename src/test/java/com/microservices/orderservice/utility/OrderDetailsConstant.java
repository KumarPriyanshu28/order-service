package com.microservices.orderservice.utility;

import com..orderservice.dto.OrderDto;
import com..orderservice.dto.OrderLineDto;
import com..orderservice.dto.ProductDto;
import com..orderservice.entity.Order;
import com..orderservice.entity.OrderLine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class OrderDetailsConstant {
    private static final Object obj;

    static {
        try {
            obj = new JSONParser().parse(new FileReader("src/test/resources/constant.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static final JSONObject jsonObject = (JSONObject) obj;

    public static final Long PRODUCT_ID_ONE = (Long) jsonObject.get("productIdOne");
    public static final String PRODUCT_NAME_ONE = (String) jsonObject.get("productNameOne");
    public static final Double PRODUCT_PRICE_ONE = (Double) jsonObject.get("productPriceOne");
    public static final Long PRODUCT_ID_TWO = (Long) jsonObject.get("productIdTwo");
    public static final String PRODUCT_NAME_TWO = (String) jsonObject.get("productNameTwo");
    public static final Double PRODUCT_PRICE_TWO = (Double) jsonObject.get("productPriceTwo");
    public static final Long ORDER_LINE_ID_ONE = (Long) jsonObject.get("orderLineIdOne");
    public static final Long ORDER_LINE_PRODUCT_ID_ONE = (Long) jsonObject.get("orderLineProductIdOne");
    public static final Integer ORDER_LINE_QUANTITY_ONE = Integer.parseInt(String.valueOf(jsonObject.get("orderLineQuantityOne")));
    public static final Long ORDER_LINE_ID_TWO = (Long) jsonObject.get("orderLineIdTwo");
    public static final Long ORDER_LINE_PRODUCT_ID_TWO = (Long) jsonObject.get("orderLineProductIdTwo");
    public static final Integer ORDER_LINE_QUANTITY_TWO = Integer.parseInt(String.valueOf(jsonObject.get("orderLineQuantityTwo")));
    public static final Long ORDER_LINE_ID_THREE = (Long) jsonObject.get("orderLineIdThree");
    public static final Long ORDER_LINE_PRODUCT_ID_THREE = (Long) jsonObject.get("orderLineProductIdThree");
    public static final Integer ORDER_LINE_QUANTITY_THREE = Integer.parseInt(String.valueOf(jsonObject.get("orderLineQuantityThree")));
    public static final Long ORDER_LINE_ID_FOUR = (Long) jsonObject.get("orderLineIdFour");
    public static final Long ORDER_LINE_PRODUCT_ID_FOUR = (Long) jsonObject.get("orderLineProductIdFour");
    public static final Integer ORDER_LINE_QUANTITY_FOUR = Integer.parseInt(String.valueOf(jsonObject.get("orderLineQuantityFour")));
    public static final Long ORDER_ID_ONE = (Long) jsonObject.get("orderIdOne");
    public static final String ORDER_CREATED_DATE_ONE = (String) jsonObject.get("orderCreatedDateOne");
    public static final String ORDER_MODIFIED_DATE_ONE = (String) jsonObject.get("orderModifiedDateOne");
    public static final Double ORDER_TOTAL_PRICE_ONE = (Double) jsonObject.get("orderTotalPriceOne");
    public static final Long ORDER_ID_TWO = (Long) jsonObject.get("orderIdTwo");
    public static final String ORDER_CREATED_DATE_TWO = (String) jsonObject.get("orderCreatedDateTwo");
    public static final String ORDER_MODIFIED_DATE_TWO = (String) jsonObject.get("orderModifiedDateTwo");
    public static final Double ORDER_TOTAL_PRICE_TWO = (Double) jsonObject.get("orderTotalPriceTwo");
    public static final Long INVALID_ORDER_ID = (Long) jsonObject.get("orderIdInvalid");
    public static final Long NON_EXISTENT_ORDER_ID = (Long) jsonObject.get("orderIdNonExistent");

    public static final ProductDto productDtoOne = new ProductDto(PRODUCT_ID_ONE, PRODUCT_NAME_ONE, PRODUCT_PRICE_ONE);
    public static final ProductDto productDtoTwo = new ProductDto(PRODUCT_ID_TWO, PRODUCT_NAME_TWO, PRODUCT_PRICE_TWO);
    public static final OrderLineDto orderLineDtoOne = new OrderLineDto(ORDER_LINE_ID_ONE, ORDER_LINE_PRODUCT_ID_ONE, ORDER_LINE_QUANTITY_ONE, null);
    public static final OrderLineDto orderLineDtoTwo = new OrderLineDto(ORDER_LINE_ID_TWO, ORDER_LINE_PRODUCT_ID_TWO, ORDER_LINE_QUANTITY_TWO, null);
    public static final OrderLineDto orderLineDtoThree = new OrderLineDto(ORDER_LINE_ID_THREE, ORDER_LINE_PRODUCT_ID_THREE, ORDER_LINE_QUANTITY_THREE, null);
    public static final OrderLineDto orderLineDtoFour = new OrderLineDto(ORDER_LINE_ID_FOUR, ORDER_LINE_PRODUCT_ID_FOUR, ORDER_LINE_QUANTITY_FOUR, null);
    public static final OrderDto orderDtoOne = new OrderDto(ORDER_ID_ONE, LocalDateTime.parse(ORDER_CREATED_DATE_ONE), LocalDateTime.parse(ORDER_MODIFIED_DATE_ONE), ORDER_TOTAL_PRICE_ONE, List.of(orderLineDtoOne, orderLineDtoTwo));
    public static final OrderDto orderDtoTwo = new OrderDto(ORDER_ID_TWO, LocalDateTime.parse(ORDER_CREATED_DATE_TWO), LocalDateTime.parse(ORDER_MODIFIED_DATE_TWO), ORDER_TOTAL_PRICE_TWO, List.of(orderLineDtoThree, orderLineDtoFour));
    public static final List<OrderDto> expectedOrderListDto = List.of(orderDtoOne, orderDtoTwo);
    public static final OrderDto orderDto = new OrderDto(ORDER_ID_ONE, LocalDateTime.parse(ORDER_CREATED_DATE_ONE), LocalDateTime.parse(ORDER_MODIFIED_DATE_ONE), ORDER_TOTAL_PRICE_ONE, List.of(orderLineDtoOne, orderLineDtoTwo));
    public static final OrderDto expectedOrderDto = new OrderDto(ORDER_ID_ONE, LocalDateTime.parse(ORDER_CREATED_DATE_ONE), LocalDateTime.parse(ORDER_MODIFIED_DATE_ONE), ORDER_TOTAL_PRICE_ONE, List.of(orderLineDtoOne, orderLineDtoTwo));
    public static final OrderLine orderLineOne = new OrderLine(ORDER_LINE_ID_ONE, ORDER_LINE_PRODUCT_ID_ONE, ORDER_LINE_QUANTITY_ONE, null);
    public static final OrderLine orderLineTwo = new OrderLine(ORDER_LINE_ID_TWO, ORDER_LINE_PRODUCT_ID_TWO, ORDER_LINE_QUANTITY_TWO, null);
    public static final OrderLine orderLineThree = new OrderLine(ORDER_LINE_ID_THREE, ORDER_LINE_PRODUCT_ID_THREE, ORDER_LINE_QUANTITY_THREE, null);
    public static final OrderLine orderLineFour = new OrderLine(ORDER_LINE_ID_FOUR, ORDER_LINE_PRODUCT_ID_FOUR, ORDER_LINE_QUANTITY_FOUR, null);
    public static final Order orderOne = new Order(ORDER_ID_ONE, LocalDateTime.parse(ORDER_CREATED_DATE_ONE), LocalDateTime.parse(ORDER_MODIFIED_DATE_ONE), ORDER_TOTAL_PRICE_ONE, List.of(orderLineOne, orderLineTwo));
    public static final Order orderTwo = new Order(ORDER_ID_TWO, LocalDateTime.parse(ORDER_CREATED_DATE_TWO), LocalDateTime.parse(ORDER_MODIFIED_DATE_TWO), ORDER_TOTAL_PRICE_TWO, List.of(orderLineThree, orderLineFour));
    public static final List<Order> orderList = List.of(orderOne, orderTwo);
    public static final Order order = new Order(ORDER_ID_ONE, LocalDateTime.parse(ORDER_CREATED_DATE_ONE), LocalDateTime.parse(ORDER_MODIFIED_DATE_ONE), ORDER_TOTAL_PRICE_ONE, List.of(orderLineOne, orderLineTwo));
}
