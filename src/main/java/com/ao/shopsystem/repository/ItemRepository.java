package com.ao.shopsystem.repository;

import com.ao.shopsystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ao on 2018-09-21
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}
