package com.ao.shopsystem.service;

import com.ao.shopsystem.controller.dto.item.ProductDTO;
import com.ao.shopsystem.entity.Product;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.repository.ProductRepository;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
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

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(ProductDTO productDTO) {

        log.info(
                "Adding a new Product - name {} ({}) with price {}",
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice()
        );

        Product product = new Product();

        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());

        return this.productRepository.save(product);
    }

    public Product getById(Long id) throws NotFoundException {

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

    public void delete(Long id) throws NotFoundException {

        log.info("Deleting product with id {}", id);

        Optional<Product> item = this.productRepository.findById(id);

        if (!item.isPresent()) {
            log.error("No product found with id {}", id);
            throw new NotFoundException("Product is not found");
        }

        item.get().setDeletedAt(ZonedDateTime.now());

        this.productRepository.save(item.get());

        log.info("Soft-Deleted product with id {}", id);
    }
}
