package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.item.ItemDTO;
import com.ao.shopsystem.controller.dto.item.ItemQuantityPair;
import com.ao.shopsystem.controller.dto.order.OrderRequestDTO;
import com.ao.shopsystem.controller.dto.order.OrderResponseDTO;
import com.ao.shopsystem.entity.Order;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.OrderService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
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
    private OrderResponseDTO getOrder(@PathVariable Long orderId) throws NotFoundException {

        Order order = this.orderService.getOrder(orderId);

        return OrderController.convertModel(order);
    }

    @PostMapping
    private OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {

        Order order = this.orderService.createOrder(orderRequestDTO);

        return OrderController.convertModel(order);
    }

    @PutMapping("/{orderId}")
    private OrderResponseDTO updateItemQuantity(@RequestBody ItemQuantityPair itemQuantityPair,
            @PathVariable Long orderId)
            throws NotFoundException {

        Order order = this.orderService.updateOrder(
                orderId,
                itemQuantityPair.getItemId(),
                itemQuantityPair.getQuantity()
        );

        return OrderController.convertModel(order);
    }

    private static OrderResponseDTO convertModel(Order order) {

        Map<ItemDTO, Long> itemDTOLongMap = new HashMap<>();

        AtomicReference<Long> totalAmount = new AtomicReference<>(0L);

        order.getOrderToItems().forEach(
                orderToItem -> {

                    itemDTOLongMap.put(
                            ItemController.convertModel(orderToItem.getItem()),
                            orderToItem.getQuantity()
                    );

                    totalAmount.updateAndGet(
                            v -> v + orderToItem.getItem().getPrice() * orderToItem.getQuantity()
                    );
                }
        );

        return OrderResponseDTO.builder()
                .name(order.getName())
                .itemDTOLongMap(itemDTOLongMap)
                .orderTotalPrice(totalAmount.get())
                .build();
    }
}
