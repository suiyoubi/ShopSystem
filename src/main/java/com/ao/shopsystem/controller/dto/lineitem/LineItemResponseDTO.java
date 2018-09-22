package com.ao.shopsystem.controller.dto.lineitem;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * The DTO for the line item.
 * Created by ao on 2018-09-22
 */
@Builder
@Getter
@ToString
public class LineItemResponseDTO {

    private Long productId;

    private String productName;

    private Long quantity;

    private Long unitPrice;

    private Long totalPrice;
}
