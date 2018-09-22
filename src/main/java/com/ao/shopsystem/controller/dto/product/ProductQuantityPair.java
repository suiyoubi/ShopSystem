package com.ao.shopsystem.controller.dto.product;

import com.ao.shopsystem.entity.Product;
import lombok.Getter;

/**
 * The pair consist of the Id of the {@link Product} and the quantity of it.
 * Created by ao on 2018-09-22
 */
@Getter
public class ProductQuantityPair {

    private Long productId;

    private Long quantity;
}
