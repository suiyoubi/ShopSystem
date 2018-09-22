package com.ao.shopsystem.repository;

import com.ao.shopsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ao on 2018-09-22
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
