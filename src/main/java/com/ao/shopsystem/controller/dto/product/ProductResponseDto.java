package com.ao.shopsystem.controller.dto.product;

import com.ao.shopsystem.controller.dto.shop.ShopResponseDto;
import com.ao.shopsystem.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for the {@link Product}
 * Created by ao on 2018-09-21
 */
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long productId;

    private String name;

    private Long price;

    private String description;

    private ShopResponseDto shop;
}