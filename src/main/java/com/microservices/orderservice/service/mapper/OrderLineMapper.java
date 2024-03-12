package com.microservices.orderservice.service.mapper;

import com.microservices.orderservice.dto.OrderLineDto;
import com.microservices.orderservice.entity.OrderLine;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface for mapping between OrderLine and OrderLineDto entities.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Mapper
public interface OrderLineMapper {

    /**
     * Maps a OrderLineDto to a OrderLine entity.
     *
     * @param orderLineDto The Dto containing information for creating a new orderLine.
     * @return The OrderLine Product entity.
     */
    OrderLine orderLineDtoToOrderLine(OrderLineDto orderLineDto);

    /**
     * Maps a list of OrderLine entities to a list of OrderLineDto entities.
     *
     * @param orderLineDtoList The list of OrderLine entities to be mapped.
     * @return The list of mapped OrderLineDto entities.
     */
    List<OrderLine> orderLineDtoListToOrderLineList(List<OrderLineDto> orderLineDtoList);
}

