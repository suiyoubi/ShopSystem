package com.ao.shopsystem.controller.dto.order;

import com.ao.shopsystem.controller.dto.lineitem.LineItemResponseDto;
import com.ao.shopsystem.controller.dto.shop.ShopResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * The Response DTO for {@link com.ao.shopsystem.entity.Order}
 * Created by ao on 2018-09-22
 */

@Builder
@Getter
@ToString
public class OrderResponseDto {

    private Long orderId;

    private String name;

    private List<LineItemResponseDto> lineItems;

    private Long orderTotalPrice;

    private ShopResponseDto shop;
}
