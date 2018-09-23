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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * Retrieve the order entity.
     *
     * @param orderId id of the order
     * @return orderResponseDto with the specified id
     * @throws NotFoundException if no order with the id being found.
     */
    @GetMapping("/{orderId}")
    private OrderResponseDto getOrder(@PathVariable Long orderId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Order order = this.orderService.findById(orderId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "fetch the order with id {}", order.getId());

        return OrderController.convertModel(order);
    }

    /**
     * Create a new order entity
     *
     * @param orderRequestDto representation of the newly created order
     * @return orderResponseDto that has been created
     * @throws NotFoundException if {@link com.ao.shopsystem.entity.Product} can not be found
     */
    @PostMapping
    private OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto)
            throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Order order = this.orderService.createOrder(orderRequestDto);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "created the order with id {}",
                order.getId());
        return OrderController.convertModel(order);
    }

    /**
     * update the quantity of a specified order's product.
     * if item is not found in the order entity, add the new item to the order.
     *
     * @param productQuantityPair pair of product Id and new quantity
     * @param orderId the id of the order
     * @return updated OrderResponseDto
     * @throws NotFoundException no such order with orderId or item with itemId being found
     */
    @PatchMapping("/{orderId}/productQuantityPair")
    private OrderResponseDto updateProductQuantity(
            @RequestBody ProductQuantityPair productQuantityPair,
            @PathVariable Long orderId
    ) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Order order = this.orderService.updateProductInOrder(
                orderId,
                productQuantityPair.getProductId(),
                productQuantityPair.getQuantity()
        );

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "updatedProductQuantity for order: ",
                orderId);
        return OrderController.convertModel(order);
    }

    /**
     * update the shop attribute of the specified order
     *
     * @param shopId id of the shop entity
     * @param orderId id of the order entity
     * @return updated OrderResponseDto
     * @throws NotFoundException if no shop with shopId or order with orderId being found
     */
    @PatchMapping("/{orderId}/shop")
    private OrderResponseDto updateShop(@RequestBody Long shopId, @PathVariable Long orderId)
            throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Order order = this.orderService.updateShopInfo(orderId, shopId);

        log.info(
                ControllerLogHelper.SUCCESS_MESSAGE + "updated the order: {} with shop: {}",
                orderId,
                shopId
        );

        return OrderController.convertModel(order);
    }

    /**
     * soft delete the order with given id
     *
     * @param orderId the id of the order
     * @throws NotFoundException no such order with orderid being found
     */
    @DeleteMapping("/{orderId}")
    private void deleteOrder(@PathVariable Long orderId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        this.orderService.delete(orderId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "deleted the order with id {}", orderId);
    }

    /**
     * retrieve all the orders
     *
     * @return list of all the response entity of orders
     */
    @GetMapping("/all")
    public List<OrderResponseDto> getAllOrders() {

        log.info(ControllerLogHelper.NEW_API_CALL);

        List<Order> orders = this.orderService.getAll();

        return orders.stream().map(OrderController::convertModel).collect(Collectors.toList());
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
                .orderId(order.getId())
                .name(order.getName())
                .lineItems(lineItems)
                .shop(ShopController.convertModel(order.getShop()))
                .orderTotalPrice(orderTotalPrice)
                .build();
    }
}
