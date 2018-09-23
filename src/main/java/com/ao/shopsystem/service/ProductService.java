package com.ao.shopsystem.service;

import com.ao.shopsystem.controller.dto.product.ProductRequestDto;
import com.ao.shopsystem.entity.Product;
import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.repository.ProductRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service of {@link Product}
 * Created by ao on 2018-09-21
 */
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ShopService shopService;

    @Autowired
    public ProductService(ProductRepository productRepository, ShopService shopService) {

        this.productRepository = productRepository;
        this.shopService = shopService;
    }

    /**
     * create a {@link Product}
     *
     * @param productDto the Dto of the product
     * @return the created entity
     * @throws NotFoundException if no {@link com.ao.shopsystem.entity.Shop} with shopId being found
     */
    public Product create(ProductRequestDto productDto) throws NotFoundException {

        log.info(
                "Adding a new Product - name: {} ({}) with price: {} and shopId: {}",
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getShopId()
        );

        Product product = new Product();

        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        if (Objects.nonNull(productDto.getShopId())) {
            product.setShop(this.shopService.findById(productDto.getShopId()));
        }

        return this.productRepository.save(product);
    }

    /**
     * get {@link Product} by its Id (Primary Key)
     *
     * @param id the id of the product
     * @return the targeted entity
     * @throws NotFoundException if no such entity being found
     */
    public Product findById(Long id) throws NotFoundException {

        log.info("Trying to fetch product with id {}", id);

        Optional<Product> optionalItem = this.productRepository.findById(id).filter(
                item -> Objects.isNull(item.getDeletedAt())
        );

        if (optionalItem.isPresent()) {
            return optionalItem.get();
        }

        log.error("No product found with id {}", id);
        throw new NotFoundException("Product is not found");
    }

    /**
     * update the shop field in the {@link Product}
     *
     * @param productId the id of the product
     * @param shopId the id of the shop
     * @return the updated product entity
     * @throws NotFoundException if no such product or shop being found
     */
    public Product updateShopInfo(Long productId, Long shopId) throws NotFoundException {

        log.info("updating product: {} with new shop: {}", productId, shopId);

        Product product = this.findById(productId);

        Shop shop = this.shopService.findById(shopId);

        product.setShop(shop);

        return this.productRepository.save(product);
    }

    /**
     * soft delete the product by its id
     *
     * @param id the id of the product
     * @throws NotFoundException if no such entity being found
     */
    public void delete(Long id) throws NotFoundException {

        log.info("Deleting product with id {}", id);

        Optional<Product> item = this.productRepository.findById(id);

        if (!item.isPresent()) {
            log.error("No product found with id {}", id);
            throw new NotFoundException("Product is not found");
        }

        item.get().setDeletedAt(ZonedDateTime.now());

        this.productRepository.save(item.get());
    }

    /**
     * retrieve all the product entities
     *
     * @return list of all the products
     */
    public List<Product> getAll() {

        log.info("retrieving all the products");

        return this.productRepository.findAll().stream().filter(
                product -> Objects.isNull(product.getDeletedAt())
        ).collect(Collectors.toList());
    }
}
