package com.microservices.orderservice.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.OrderLineDto;
import com.microservices.orderservice.exception.OrderServiceException;
import com.microservices.orderservice.service.impl.OrderServiceImpl;
import com.microservices.orderservice.utility.ErrorMessageConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static com.microservices.orderservice.utility.ErrorCodeConstant.*;
import static com.microservices.orderservice.utility.ExceptionConstant.*;
import static com.microservices.orderservice.utility.OrderDetailsConstant.*;
import static com.microservices.orderservice.utility.UrlConstant.GENERIC_ORDERS_URL;
import static com.microservices.orderservice.utility.UrlConstant.SPECIFIC_ORDER_URL;
import static com.microservices.orderservice.utility.ValidationMessageConstant.*;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerImplTest {
    @MockBean
    private OrderServiceImpl orderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    Jackson2ObjectMapperBuilder mapperBuilder;

    @Test
    @DisplayName("GET ALL ORDERS - SUCCESS")
    void GetAllOrders_ReturnOrderList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(expectedOrderListDto);

        RequestBuilder requestBuilder = get(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String expectedResponse = mapperBuilder.build().writeValueAsString(expectedOrderListDto);
        String actualResponse = mvcResult.getResponse().getContentAsString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        verify(orderService, times(1)).getAllOrders();
    }
    @Test
    @DisplayName("GET ALL ORDERS - EMPTY ORDER LIST")
    void GetAllOrders_EmptyOrderList_ExceptionThrown() throws Exception {
        RequestBuilder requestBuilder = get(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON);
        when(orderService.getAllOrders())
                .thenThrow(new OrderServiceException(GET_ALL_ORDERS_NO_CONTENT, HttpStatus.NO_CONTENT));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.statusCode").value(GET_ALL_ORDERS_ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ErrorMessageConstant.GET_ALL_ORDERS_ERROR_MESSAGE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    @DisplayName("CREATE ORDER - SUCCESS")
    void CreateOrder_ReturnCreatedOrder() throws Exception {
        String expectedResponse = mapperBuilder.build().writeValueAsString(orderDto);

        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(expectedResponse)
                .contentType(MediaType.APPLICATION_JSON);

        when(orderService.createOrder(orderDto)).thenReturn(expectedOrderDto);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        verify(orderService, times(1)).createOrder(any(OrderDto.class));
    }

    @Test
    @DisplayName("VALIDATE ORDER - ORDER LINE NULL")
    void ValidateOrder_OrderLineNull_ReturnBadRequest() throws Exception {
        OrderDto orderDto = new OrderDto();
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].message", hasItem(ORDER_LINES_NOT_NULL_MESSAGE)))
                .andExpect(jsonPath("$[*].message", hasItem(ORDER_LINES_NOT_EMPTY_MESSAGE)))
                .andReturn();
    }

    @Test
    @DisplayName("VALIDATE ORDER - ORDER LINE EMPTY")
    void ValidateOrder_OrderLineEmpty_ReturnBadRequest() throws Exception {
        OrderDto orderDto = new OrderDto(0, null, null, 0, List.of());
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(ORDER_LINES_NOT_EMPTY_MESSAGE))
                .andReturn();
    }

    @Test
    @DisplayName("VALIDATE ORDER - PRODUCT ID NULL")
    void ValidateOrder_ProductIdNull_ReturnBadRequest() throws Exception {
        OrderLineDto orderLineDto = new OrderLineDto(0L,null, 1, null);
        OrderDto orderDto = new OrderDto(0, null, null, 0, List.of(orderLineDto));
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(PRODUCT_ID_NOT_NULL_MESSAGE))
                .andReturn();
    }

    @Test
    @DisplayName("VALIDATE ORDER - PRODUCT ID NON POSITIVE")
    void ValidateOrder_ProductIdNonPositive_ReturnBadRequest() throws Exception {
        OrderLineDto orderLineDto = new OrderLineDto(0L,-1L, 1, null);
        OrderDto orderDto = new OrderDto(0, null, null, 0, List.of(orderLineDto));
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(PRODUCT_ID_POSITIVE_MESSAGE))
                .andReturn();
    }

    @Test
    @DisplayName("VALIDATE ORDER - QUANTITY NULL")
    void ValidateOrder_QuantityNull_ReturnBadRequest() throws Exception {
        OrderLineDto orderLineDto = new OrderLineDto(0L,1L, null, null);
        OrderDto orderDto = new OrderDto(0, null, null, 0, List.of(orderLineDto));
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(QUANTITY_NOT_NULL_MESSAGE))
                .andReturn();
    }

    @Test
    @DisplayName("VALIDATE ORDER - QUANTITY NON POSITIVE")
    void ValidateOrder_QuantityNonPositive_ReturnBadRequest() throws Exception {
        OrderLineDto orderLineDto = new OrderLineDto(0L,1L, -1, null);
        OrderDto orderDto = new OrderDto(0, null, null, 0, List.of(orderLineDto));
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(QUANTITY_POSITIVE_MESSAGE))
                .andReturn();
    }

    @Test
    @DisplayName("VALIDATE ORDER - QUANTITY MAX VALUE")
    void ValidateOrder_QuantityMaxValue_ReturnBadRequest() throws Exception {
        OrderLineDto orderLineDto = new OrderLineDto(0L,1L, 1001, null);
        OrderDto orderDto = new OrderDto(0, null, null, 0, List.of(orderLineDto));
        RequestBuilder requestBuilder = post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(QUANTITY_MAXIMUM_VALUE_MESSAGE))
                .andReturn();
    }

    @Test
    @DisplayName("GET ORDER BY ID - SUCCESS")
    void GetOrderById_ReturnFetchedOrder() throws Exception {
        RequestBuilder requestBuilder = get(SPECIFIC_ORDER_URL, anyLong()).accept(MediaType.APPLICATION_JSON);

        when(orderService.getOrderById(ORDER_ID_ONE)).thenReturn(expectedOrderDto);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String expectedResponse = mapperBuilder.build().writeValueAsString(expectedOrderDto);
        String actualResponse = mvcResult.getResponse().getContentAsString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        verify(orderService, times(1)).getOrderById(anyLong());
    }

    @Test
    @DisplayName("GET ORDER BY ID - INVALID INPUT")
    void GetOrderById_InvalidInput_ExceptionThrown() throws Exception {
        RequestBuilder requestBuilder = get(SPECIFIC_ORDER_URL, INVALID_ORDER_ID).accept(MediaType.APPLICATION_JSON);
        when(orderService.getOrderById(INVALID_ORDER_ID))
                .thenThrow(new OrderServiceException(GET_ORDER_BY_ID_NOT_FOUND, HttpStatus.NOT_FOUND));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(GET_ORDER_BY_ID_ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ErrorMessageConstant.GET_ORDER_BY_ID_ERROR_MESSAGE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        verify(orderService, times(1)).getOrderById(anyLong());
    }

    @Test
    @DisplayName("GET ORDER BY ID - NON EXISTENT ORDER")
    void GetOrderById_NonExistentOrder_ExceptionThrown() throws Exception {
        RequestBuilder requestBuilder = get(SPECIFIC_ORDER_URL, NON_EXISTENT_ORDER_ID).accept(MediaType.APPLICATION_JSON);
        when(orderService.getOrderById(NON_EXISTENT_ORDER_ID))
                .thenThrow(new OrderServiceException(GET_ORDER_BY_ID_NOT_FOUND, HttpStatus.NOT_FOUND));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(GET_ORDER_BY_ID_ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ErrorMessageConstant.GET_ORDER_BY_ID_ERROR_MESSAGE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        verify(orderService, times(1)).getOrderById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - SUCCESS")
    void DeleteOrderById_ReturnDeletedOrder() throws Exception {
        RequestBuilder requestBuilder = delete(SPECIFIC_ORDER_URL, anyLong()).accept(MediaType.APPLICATION_JSON);

        when(orderService.deleteOrderById(ORDER_ID_ONE)).thenReturn(expectedOrderDto);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String expectedResponse = mapperBuilder.build().writeValueAsString(expectedOrderDto);
        String actualResponse = mvcResult.getResponse().getContentAsString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        verify(orderService, times(1)).deleteOrderById(anyLong());
    }
    @Test
    @DisplayName("DELETE ORDER BY ID - INVALID INPUT")
    void DeleteOrderById_InvalidInput_ExceptionThrown() throws Exception {
        RequestBuilder requestBuilder = delete(SPECIFIC_ORDER_URL, INVALID_ORDER_ID).accept(MediaType.APPLICATION_JSON);
        when(orderService.deleteOrderById(INVALID_ORDER_ID))
                .thenThrow(new OrderServiceException(DELETE_ORDER_BY_ID_NOT_FOUND, HttpStatus.NOT_FOUND));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(DELETE_ORDER_BY_ID_ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ErrorMessageConstant.DELETE_ORDER_BY_ID_ERROR_MESSAGE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        verify(orderService, times(1)).deleteOrderById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - NON EXISTENT ORDER")
    void DeleteOrderById_NonExistentOrder_ExceptionThrown() throws Exception {
        RequestBuilder requestBuilder = delete(SPECIFIC_ORDER_URL, NON_EXISTENT_ORDER_ID).accept(MediaType.APPLICATION_JSON);
        when(orderService.deleteOrderById(NON_EXISTENT_ORDER_ID))
                .thenThrow(new OrderServiceException(DELETE_ORDER_BY_ID_NOT_FOUND, HttpStatus.NOT_FOUND));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(DELETE_ORDER_BY_ID_ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ErrorMessageConstant.DELETE_ORDER_BY_ID_ERROR_MESSAGE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        verify(orderService, times(1)).deleteOrderById(anyLong());
    }

    @ParameterizedTest
    @ValueSource(strings = {"getAllOrders",
                            "createOrder",
                            "getOrderById",
                            "deleteOrderById"})
    @DisplayName("EXCEPTION HANDLING FOR ALL METHODS - GENERIC EXCEPTION")
    void AllMethods_ExceptionOccurred_GenericExceptionThrown(String methodName) throws Exception {
        RequestBuilder requestBuilder = getRequestBuilderForMethod(methodName);
        when(invokeMethodWithException(methodName)).thenThrow(new RuntimeException("Some exception occurred."));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        verifyProductServiceMethodCalledOnce(methodName);
    }

    private RequestBuilder getRequestBuilderForMethod(String methodName) throws JsonProcessingException {
        return switch (methodName) {
            case "getAllOrders" -> get(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON);
            case "createOrder" -> post(GENERIC_ORDERS_URL).accept(MediaType.APPLICATION_JSON)
                                  .content(mapperBuilder.build().writeValueAsString(orderDto))
                                  .contentType(MediaType.APPLICATION_JSON);
            case "getOrderById" -> get(SPECIFIC_ORDER_URL, anyLong()).accept(MediaType.APPLICATION_JSON);
            case "deleteOrderById" -> delete(SPECIFIC_ORDER_URL, anyLong()).accept(MediaType.APPLICATION_JSON);
            default -> throw new IllegalArgumentException("Unsupported method: " + methodName);
        };
    }

    private Exception invokeMethodWithException(String methodName) {
        switch (methodName) {
            case "getAllOrders" -> orderService.getAllOrders();
            case "createOrder" -> orderService.createOrder(orderDto);
            case "getOrderById" -> orderService.getOrderById(ORDER_ID_ONE);
            case "deleteOrderById" -> orderService.deleteOrderById(ORDER_ID_ONE);
            default -> throw new IllegalArgumentException("Unsupported method: " + methodName);
        }
        return null;
    }

    private void verifyProductServiceMethodCalledOnce(String methodName) {
        switch (methodName) {
            case "getAllOrders" -> verify(orderService, times(1)).getAllOrders();
            case "createOrder" -> verify(orderService, times(1)).createOrder(any(OrderDto.class));
            case "getOrderById" -> verify(orderService, times(1)).getOrderById(anyLong());
            case "deleteOrderById" -> verify(orderService, times(1)).deleteOrderById(anyLong());
            default -> throw new IllegalArgumentException("Unsupported method: " + methodName);
        }
    }

}
