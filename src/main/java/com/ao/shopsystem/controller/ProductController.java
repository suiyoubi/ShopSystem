package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.item.ProductDTO;
import com.ao.shopsystem.entity.Product;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.ProductService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of the {@link Product}
 * Created by ao on 2018-09-21
 */

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final String NEW_API_CALL = "NEW API Call: ";
    private static final String FAILURE_MESSAGE = "Failure: ";
    private static final String SUCCESS_MESSAGE = "Success: ";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ProductDTO getItem(@PathVariable Long productId) throws NotFoundException {

        log.info(NEW_API_CALL + "Trying to fetch item with id {}", productId);

        Product product = this.productService.getById(productId);

        if (Objects.isNull(product)) {
            log.error(FAILURE_MESSAGE + "No item found with id {}", productId);

            throw new NotFoundException("No item found with id");
        }

        log.info(SUCCESS_MESSAGE + "Fetch the item with id {}", productId);

        return ProductController.convertModel(product);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {

        log.info(NEW_API_CALL);

        Product product = this.productService.create(productDTO);

        return ProductController.convertModel(product);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) throws NotFoundException {

        log.info(NEW_API_CALL);

        this.productService.delete(productId);
    }

    private static ProductDTO convertModel(Product product) {
        return ProductDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    private static Product convertToEntity(ProductDTO productDTO) {

        Product product = new Product();

        product.setPrice(product.getPrice());
        product.setName(product.getName());
        product.setDescription(product.getDescription());

        return product;
    }
}
