package com.ao.shopsystem.controller.dto.item;

import lombok.Getter;

/**
 * The pair consist of the Id of the {@link com.ao.shopsystem.entity.Item} and the quantity of it.
 * Created by ao on 2018-09-22
 */
@Getter
public class ItemQuantityPair {

    private Long itemId;

    private Long quantity;
}
