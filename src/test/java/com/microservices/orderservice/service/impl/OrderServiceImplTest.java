package com.microservices.orderservice.service.impl;

import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.dto.ProductDto;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.exception.OrderServiceException;
import com.microservices.orderservice.proxy.ProductServiceProxy;
import com.microservices.orderservice.repository.OrderRepository;
import com.microservices.orderservice.service.mapper.OrderLineMapper;
import com.microservices.orderservice.service.mapper.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.microservices.orderservice.utility.OrderDetailsConstant.*;
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
        when(orderRepository.findAll()).thenReturn(orderList);
        when(orderMapper.orderListToOrderDtoList(orderList)).thenReturn(expectedOrderListDto);

        List<OrderDto> actualOrderList = orderService.getAllOrders();

        assertNotNull(actualOrderList);
        assertIterableEquals(expectedOrderListDto, actualOrderList);
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
        ResponseEntity<ProductDto> responseEntityOne = ResponseEntity.ok(productDtoOne);
        when(productServiceProxy.getProductById(PRODUCT_ID_ONE)).thenReturn(responseEntityOne);
        ResponseEntity<ProductDto> responseEntityTwo = ResponseEntity.ok(productDtoTwo);
        when(productServiceProxy.getProductById(PRODUCT_ID_TWO)).thenReturn(responseEntityTwo);

        when(orderRepository.save(orderOne)).thenReturn(orderOne);
        when(orderMapper.orderDtoToOrder(orderDtoOne)).thenReturn(orderOne);
        when(orderMapper.orderToOrderDto(orderOne)).thenReturn(orderDtoOne);

        OrderDto actualOrderResponseDto = orderService.createOrder(orderDtoOne);

        assertNotNull(actualOrderResponseDto);
        assertEquals(expectedOrderDto, actualOrderResponseDto);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("GET ORDER BY ID - SUCCESS")
    void GetOrderById_ReturnFetchedOrder() {
        when(orderRepository.findById(ORDER_ID_ONE)).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);

        OrderDto actualOrderResponseDto = orderService.getOrderById(ORDER_ID_ONE);

        assertNotNull(actualOrderResponseDto);
        assertEquals(expectedOrderDto, actualOrderResponseDto);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("GET ORDER BY ID - INVALID INPUT")
    void GetOrderById_InvalidInput_ExceptionThrown() {
        when(orderRepository.findById(INVALID_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.getOrderById(INVALID_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("GET ORDER BY ID - NON EXISTENT ORDER")
    void GetOrderById_NonExistentOrder_ExceptionThrown() {
        when(orderRepository.findById(NON_EXISTENT_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.getOrderById(NON_EXISTENT_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - SUCCESS")
    void DeleteOrderById_ReturnDeletedOrder() {
        when(orderRepository.findById(ORDER_ID_ONE)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).deleteById(ORDER_ID_ONE);
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);

        OrderDto actualOrderResponseDto = orderService.deleteOrderById(ORDER_ID_ONE);

        assertNotNull(actualOrderResponseDto);
        assertEquals(expectedOrderDto, actualOrderResponseDto);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - INVALID INPUT")
    void DeleteOrderById_InvalidInput_ExceptionThrown() {
        when(orderRepository.findById(INVALID_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.deleteOrderById(INVALID_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("DELETE ORDER BY ID - NON EXISTENT ORDER")
    void DeleteOrderById_NonExistentOrder_ExceptionThrown() {
        when(orderRepository.findById(NON_EXISTENT_ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderServiceException.class, () -> orderService.deleteOrderById(NON_EXISTENT_ORDER_ID));
        verify(orderRepository, times(1)).findById(anyLong());
    }

}
