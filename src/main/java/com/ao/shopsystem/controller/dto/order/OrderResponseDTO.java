package com.ao.shopsystem.controller.dto.order;

import com.ao.shopsystem.controller.dto.item.ItemDTO;
import java.util.Map;
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
public class OrderResponseDTO {

    private String name;

    private Map<ItemDTO, Long> itemDTOLongMap;

    private Long orderTotalPrice;
}
