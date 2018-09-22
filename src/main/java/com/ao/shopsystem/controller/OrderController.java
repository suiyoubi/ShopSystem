package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.lineitem.LineItemResponseDto;
import com.ao.shopsystem.controller.dto.order.OrderRequestDto;
import com.ao.shopsystem.controller.dto.order.OrderResponseDto;
import com.ao.shopsystem.controller.dto.product.ProductQuantityPair;
import com.ao.shopsystem.entity.Order;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.OrderService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for {@link Order}
 * Created by ao on 2018-09-22
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    private OrderResponseDto getOrder(@PathVariable Long orderId) throws NotFoundException {

        Order order = this.orderService.getOrder(orderId);

        return OrderController.convertModel(order);
    }

    @PostMapping
    private OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto)
            throws NotFoundException {

        Order order = this.orderService.createOrder(orderRequestDto);

        return OrderController.convertModel(order);
    }

    @PutMapping("/{orderId}")
    private OrderResponseDto updateItemQuantity(
            @RequestBody ProductQuantityPair productQuantityPair,
            @PathVariable Long orderId
    ) throws NotFoundException {

        Order order = this.orderService.updateOrder(
                orderId,
                productQuantityPair.getProductId(),
                productQuantityPair.getQuantity()
        );

        return OrderController.convertModel(order);
    }

    private static OrderResponseDto convertModel(Order order) {

        List<LineItemResponseDto> lineItems = order.getLineItems().stream().map(
                lineItem -> LineItemResponseDto.builder()
                        .productId(lineItem.getProduct().getId())
                        .productName(lineItem.getProduct().getName())
                        .quantity(lineItem.getQuantity())
                        .unitPrice(lineItem.getProduct().getPrice())
                        .totalPrice(lineItem.getProduct().getPrice() * lineItem.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        Long orderTotalPrice = lineItems.stream()
                .map(LineItemResponseDto::getTotalPrice)
                .reduce((x, y) -> x + y)
                .orElse(0L);

        return OrderResponseDto.builder()
                .name(order.getName())
                .lineItems(lineItems)
                .shop(ShopController.convertModel(order.getShop()))
                .orderTotalPrice(orderTotalPrice)
                .build();
    }
}
