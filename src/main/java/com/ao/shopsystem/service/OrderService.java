package com.ao.shopsystem.service;

import com.ao.shopsystem.controller.dto.order.OrderRequestDto;
import com.ao.shopsystem.entity.LineItem;
import com.ao.shopsystem.entity.Order;
import com.ao.shopsystem.entity.Product;
import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.repository.OrderRepository;
import java.time.ZonedDateTime;
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

    private final ProductService productService;
    private final ShopService shopService;

    public OrderService(
            OrderRepository orderRepository,
            ProductService productService,
            ShopService shopService
    ) {

        this.orderRepository = orderRepository;
        this.productService = productService;
        this.shopService = shopService;
    }

    /**
     * retrieve the {@link Order} with the given id
     *
     * @param id the id of order
     * @return the entity of oder
     * @throws NotFoundException if no such entity being found
     */
    public Order findById(Long id) throws NotFoundException {

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

    /**
     * update the {@link Order} such that the {@link Product} that associated with now have newQuantity.
     * If the product is not in the order, add it to the oder with the newQuantity.
     *
     * @param orderId Id of the Order
     * @param itemId Id of the Item
     * @param newQuantity the new quantity of the product that will be used to replace the old value
     * @return the updated {@link Order} entity
     * @throws NotFoundException if no order with orderId or product with itemId being found
     */
    public Order updateProductInOrder(Long orderId, Long itemId, Long newQuantity)
            throws NotFoundException {

        log.info("Trying to fetch order with id {}", orderId);

        Optional<Order> optionalOrder = this.orderRepository.findById(orderId).filter(
                order -> Objects.isNull(order.getDeletedAt())
        );

        if (!optionalOrder.isPresent()) {
            log.error("Order with id {} is not found", orderId);
            throw new NotFoundException("Order Id is not found");
        }

        Order order = this.findById(orderId);

        Product product = this.productService.findById(itemId);

        Optional<LineItem> orderToItemOptional = order.getLineItems().stream().filter(
                lineItem -> lineItem.getProduct().equals(product)
        ).findFirst();

        LineItem lineItem;

        if (!orderToItemOptional.isPresent()) {

            // The product is not in the order, add to the order
            lineItem = new LineItem();

            lineItem.setQuantity(newQuantity);
            lineItem.setProduct(product);
            lineItem.setOrder(order);

            order.getLineItems().add(lineItem);
        } else {

            // The product is in the order, update the quantity
            lineItem = orderToItemOptional.get();
            lineItem.setQuantity(newQuantity);
        }

        return this.orderRepository.save(order);
    }

    /**
     * create the {@link} Order
     *
     * @param orderRequestDto The request DTO for order
     * @return the entity of the created order
     */
    public Order createOrder(OrderRequestDto orderRequestDto) throws NotFoundException {

        log.info(
                "Adding a new order - name {} with items {} in shop: {}",
                orderRequestDto.getName(),
                orderRequestDto.getItems(),
                orderRequestDto.getShopId()
        );

        Order order = new Order();

        order.setName(orderRequestDto.getName());

        List<LineItem> lineItems = new LinkedList<>();

        if (Objects.nonNull(orderRequestDto.getItems())) {
            orderRequestDto.getItems().forEach(
                    (productId, quantity) -> {

                        Product product;
                        try {
                            product = this.productService.findById(productId);
                        } catch (NotFoundException e) {

                            throw new RuntimeException(e);
                        }

                        LineItem lineItem = new LineItem();

                        lineItem.setProduct(product);
                        lineItem.setQuantity(quantity);
                        lineItem.setOrder(order);

                        lineItems.add(lineItem);
                    }
            );
        }

        order.setLineItems(lineItems);

        // Order with no shop information is allowed
        if (Objects.nonNull(orderRequestDto.getShopId())) {
            order.setShop(this.shopService.findById(orderRequestDto.getShopId()));
        }

        return this.orderRepository.save(order);
    }

    /**
     * update the shop field in the {@link Order}
     *
     * @param orderId the id of the order
     * @param shopId the id of the shop
     * @return the updated product entity
     * @throws NotFoundException if no such order or shop being found
     */
    public Order updateShopInfo(Long orderId, Long shopId) throws NotFoundException {

        log.info("updating order: {} with new shop: {}", orderId, shopId);

        Order order = findById(orderId);

        Shop shop = this.shopService.findById(shopId);

        order.setShop(shop);

        return this.orderRepository.save(order);
    }

    /**
     * soft delete the order with specified Id
     *
     * @param orderId id of the order to be deleted
     * @throws NotFoundException no such order being found
     */
    public void delete(Long orderId) throws NotFoundException {

        log.info("deleting order with id: {}", orderId);

        Order order = findById(orderId);

        // Also delete all the line items of the order
        order.getLineItems().forEach(
                lineItem -> lineItem.setDeletedAt(ZonedDateTime.now())
        );

        order.setDeletedAt(ZonedDateTime.now());

        this.orderRepository.save(order);
    }
}
