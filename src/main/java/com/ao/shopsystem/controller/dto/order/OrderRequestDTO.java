package com.ao.shopsystem.controller.dto.order;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The Request DTO for {@link com.ao.shopsystem.entity.Order}
 * Created by ao on 2018-09-22
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private String name;

    private Map<Long, Long> items;
}
