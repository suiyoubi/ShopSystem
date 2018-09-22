package com.ao.shopsystem.controller.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Request DTO of {@link com.ao.shopsystem.entity.Shop}
 * Created by ao on 2018-09-22
 */
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopRequestDto {

    private Long shopId;

    private String name;

    private String address;
}
