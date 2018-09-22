package com.ao.shopsystem.controller.dto.item;

import com.ao.shopsystem.entity.Product;
import lombok.Getter;

/**
 * The pair consist of the Id of the {@link Product} and the quantity of it.
 * Created by ao on 2018-09-22
 */
@Getter
public class ItemQuantityPair {

    private Long itemId;

    private Long quantity;
}
