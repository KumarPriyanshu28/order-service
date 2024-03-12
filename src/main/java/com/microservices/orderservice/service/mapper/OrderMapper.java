package com.microservices.orderservice.service.mapper;

import com.microservices.orderservice.dto.OrderDto;
import com.microservices.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for mapping between Order and OrderDto entities.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Mapper
public interface OrderMapper {

    /**
     * Maps an OrderDto to an Order entity, ignoring certain fields.
     *
     * @param orderDto The OrderDto to be mapped.
     * @return The mapped Order entity.
     */
    @Mapping(source = "orderId", target = "orderId", ignore = true)
    @Mapping(source = "createdDate", target = "createdDate", ignore = true)
    @Mapping(source = "modifiedDate", target = "modifiedDate", ignore = true)
    @Mapping(target = "orderLineList", source = "orderLineDtoList")
    Order orderDtoToOrder(OrderDto orderDto);

    /**
     * Maps an Order entity to an OrderDto.
     *
     * @param order The Order entity to be mapped.
     * @return The mapped OrderDto.
     */
    @Mapping(target = "orderLineDtoList", source = "orderLineList")
    OrderDto orderToOrderDto(Order order);

    /**
     * Maps a list of Order entities to a list of OrderDto entities.
     *
     * @param orderList The list of Order entities to be mapped.
     * @return The list of mapped OrderDto entities.
     */
    List<OrderDto> orderListToOrderDtoList(List<Order> orderList);
}

