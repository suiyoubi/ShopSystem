package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.product.ProductRequestDto;
import com.ao.shopsystem.controller.dto.product.ProductResponseDto;
import com.ao.shopsystem.entity.Product;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.ProductService;
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

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getItem(@PathVariable Long productId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Product product = this.productService.findById(productId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "Fetch the product with id {}", productId);

        return ProductController.convertModel(product);
    }

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto)
            throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Product product = this.productService.create(productRequestDto);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "created the product with id {}",
                product.getId());

        return ProductController.convertModel(product);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        this.productService.delete(productId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "deleted the product with id {}", productId);
    }

    private static ProductResponseDto convertModel(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .shop(ShopController.convertModel(product.getShop()))
                .description(product.getDescription())
                .build();
    }
}
