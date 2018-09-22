package com.ao.shopsystem.controller.dto.shop;

import lombok.Builder;
import lombok.Getter;

/**
 * The response DTO of {@link com.ao.shopsystem.entity.Shop}
 * Created by ao on 2018-09-22
 */
@Builder
@Getter
public class ShopResponseDto {

    private Long shopId;

    private String name;

    private String address;
}
