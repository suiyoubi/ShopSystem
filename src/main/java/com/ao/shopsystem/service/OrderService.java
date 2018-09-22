package com.ao.shopsystem.service;

import com.ao.shopsystem.controller.dto.order.OrderRequestDTO;
import com.ao.shopsystem.entity.Item;
import com.ao.shopsystem.entity.Order;
import com.ao.shopsystem.entity.OrderToItem;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.repository.OrderRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The service for the {@link Order}
 * Created by ao on 2018-09-22
 */
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemService itemService;

    public OrderService(OrderRepository orderRepository, ItemService itemService) {

        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    /** retrieve the {@link Order} with the given id
     *
     * @param id the id of order
     * @return the entity of oder
     * @throws NotFoundException if no such entity being found
     */
    public Order getOrder(Long id) throws NotFoundException {

        log.info("Trying to fetch order with id {}", id);

        Optional<Order> optionalOrder = this.orderRepository.findById(id).filter(
                order -> Objects.isNull(order.getDeletedAt())
        );

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }

        log.error("No order found with id {}", id);

        throw new NotFoundException("Order is not found");
    }

    /** update the {@link Order} such that the {@link Item} that associated with now have newQuantity.
     * If the item is not in the order, add it to the oder with the newQuantity.
     *
     * @param orderId Id of the Order
     * @param itemId Id of the Item
     * @param newQuantity the new quantity of the item that will be used to replace the old value
     * @return the updated {@link Order} entity
     * @throws NotFoundException if no order with orderId or item with itemId being found
     */
    public Order updateOrder(Long orderId, Long itemId, Long newQuantity) throws NotFoundException {

        log.info("Trying to fetch order with id {}", orderId);

        Optional<Order> optionalOrder = this.orderRepository.findById(orderId).filter(
                order -> Objects.isNull(order.getDeletedAt())
        );

        if (!optionalOrder.isPresent()) {
            log.error("Order with id {} is not found", orderId);
            throw new NotFoundException("Order Id is not found");
        }

        Order order = this.getOrder(orderId);

        Item item = this.itemService.getById(itemId);

        Optional<OrderToItem> orderToItemOptional = order.getOrderToItems().stream().filter(
                orderToItem -> orderToItem.getItem().equals(item)
        ).findFirst();

        OrderToItem orderToItem;


        if (!orderToItemOptional.isPresent()) {

            // The item is not in the order, add to the order
            orderToItem = new OrderToItem();

            orderToItem.setQuantity(newQuantity);
            orderToItem.setItem(item);
            orderToItem.setOrder(order);

            order.getOrderToItems().add(orderToItem);
        } else {

            // The item is in the order, update the quantity
            orderToItem = orderToItemOptional.get();
            orderToItem.setQuantity(newQuantity);
        }

        return this.orderRepository.save(order);
    }

    /** create the {@link} Order
     *
     * @param orderRequestDTO The request DTO for order
     * @return the entity of the created order
     */
    public Order createOrder(OrderRequestDTO orderRequestDTO) {

        log.info(
                "Adding a new order - name {} with items {}",
                orderRequestDTO.getName(),
                orderRequestDTO.getItems()
        );

        Order order = new Order();

        order.setName(orderRequestDTO.getName());

        List<OrderToItem> orderToItems = new LinkedList<>();

        if (Objects.nonNull(orderRequestDTO.getItems())) {
            orderRequestDTO.getItems().forEach(
                    (itemId, quantity) -> {

                        Item item;
                        try {
                            item = this.itemService.getById(itemId);
                        } catch (NotFoundException e) {

                            throw new RuntimeException(e);
                        }

                        OrderToItem orderToItem = new OrderToItem();

                        orderToItem.setItem(item);
                        orderToItem.setQuantity(quantity);
                        orderToItem.setOrder(order);

                        orderToItems.add(orderToItem);
                    }
            );
        }

        order.setOrderToItems(orderToItems);

        return this.orderRepository.save(order);
    }
}
