package com.microservices.orderservice.service.impl;

import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.ProductDto;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.exception.OrderServiceException;
import com.microservices.orderservice.proxy.ProductServiceProxy;
import com.microservices.orderservice.repository.OrderRepository;
import com.microservices.orderservice.service.mapper.OrderLineMapper;
import com.microservices.orderservice.service.mapper.OrderMapper;
import com.microservices.orderservice.utility.OrderDetailsConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderLineMapper orderLineMapper;
    @Mock
    private ProductServiceProxy productServiceProxy;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("GET ALL ORDERS - SUCCESS")
    void GetAllOrders_ReturnOrderList() {
        when(orderRepository.findAll()).thenReturn(OrderDetailsConstant.orderList);
        when(orderMapper.orderListToOrderDtoList(OrderDetailsConstant.orderList)).thenReturn(OrderDetailsConstant.expectedOrderListDto);

        List<OrderDto> actualOrderList = orderService.getAllOrders();

        assertNotNull(actualOrderList);
        assertIterableEquals(OrderDetailsConstant.expectedOrderListDto, actualOrderList);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GET ALL ORDERS - EMPTY ORDER LIST")
    void GetAllOrders_EmptyOrderList_ExceptionThrown() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(OrderServiceException.class, () -> orderService.getAllOrders());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("CREATE ORDER - SUCCESS")
    void CreateOrder_ReturnCreatedOrder() {
        ResponseEntity<ProductDto> responseEntityOne = ResponseEntity.ok(OrderDetailsConstant.productDtoOne);
        when(productServiceProxy.getProductById(OrderDetailsConstant.PRODUCT_ID_ONE)).thenReturn(responseEntityOne);
        ResponseEntity<ProductDto> responseEntityTwo = ResponseEntity.ok(OrderDetailsConstant.productDtoTwo);
        when(productServiceProxy.getProductById(OrderDetailsConstant.PRODUCT_ID_TWO)).thenReturn(responseEntityTwo);

        when(orderRepository.save(OrderDetailsConstant.orderOne)).thenReturn(OrderDetailsConstant.orderOne);
        when(orderMapper.orderDtoToOrder(OrderDetailsConstant.orderDtoOne)).thenReturn(OrderDetailsConstant.orderOne);
        when(orderMapper.orderToOrderDto(OrderDetailsConstant.orderOne)).thenReturn(OrderDetailsConstant.orderDtoOne);

        OrderDto actualOrderResponseDto = orderService.createOrder(OrderDetailsConstant.orderDtoOne);

        assertNotNull(actualOrderResponseDto);
        assertEquals(OrderDetailsConstant.expectedOrderDto, actualOrderResponseDto);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("GET ORDER BY ID - SUCCESS")
    void GetOrderById_ReturnFetchedOrder() {
        when(orderRepository.findById(OrderDetailsConstant.ORDER_ID_ONE)).thenReturn(Optional.of(OrderDetailsConstant.order));
        when(orderMapper.orderToOrderDto(OrderDetailsConstant.order)).thenReturn(OrderDetailsConstant.orderDto);

        OrderDto actualOrderResponseDto = orderService.getOrderById(OrderDetailsConstant.ORDER_ID_ONE);

        assertNotNull(actualOrderResponseDto);
        assertEquals(OrderDetailsConstant.expectedOrderDto, actualOrderResponseDto);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("GET ORDER BY ID - INVALID INPUT")
    void GetOrderById_InvalidInput_ExceptionThrown() {
        when(orderRepository.findById(OrderDetailsConstant.INVALID_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.getOrderById(OrderDetailsConstant.INVALID_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("GET ORDER BY ID - NON EXISTENT ORDER")
    void GetOrderById_NonExistentOrder_ExceptionThrown() {
        when(orderRepository.findById(OrderDetailsConstant.NON_EXISTENT_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.getOrderById(OrderDetailsConstant.NON_EXISTENT_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - SUCCESS")
    void DeleteOrderById_ReturnDeletedOrder() {
        when(orderRepository.findById(OrderDetailsConstant.ORDER_ID_ONE)).thenReturn(Optional.of(OrderDetailsConstant.order));
        doNothing().when(orderRepository).deleteById(OrderDetailsConstant.ORDER_ID_ONE);
        when(orderMapper.orderToOrderDto(OrderDetailsConstant.order)).thenReturn(OrderDetailsConstant.orderDto);

        OrderDto actualOrderResponseDto = orderService.deleteOrderById(OrderDetailsConstant.ORDER_ID_ONE);

        assertNotNull(actualOrderResponseDto);
        assertEquals(OrderDetailsConstant.expectedOrderDto, actualOrderResponseDto);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - INVALID INPUT")
    void DeleteOrderById_InvalidInput_ExceptionThrown() {
        when(orderRepository.findById(OrderDetailsConstant.INVALID_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.deleteOrderById(OrderDetailsConstant.INVALID_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - NON EXISTENT ORDER")
    void DeleteOrderById_NonExistentOrder_ExceptionThrown() {
        when(orderRepository.findById(OrderDetailsConstant.NON_EXISTENT_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.deleteOrderById(OrderDetailsConstant.NON_EXISTENT_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

}
