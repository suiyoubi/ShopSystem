package com.ao.shopsystem.controller.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The request DTO for {@link com.ao.shopsystem.entity.Product}
 * Created by ao on 2018-09-22
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String name;

    private Long price;

    private String description;

    private Long shopId;
}
