package com.ao.shopsystem.repository;

import com.ao.shopsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ao on 2018-09-21
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
