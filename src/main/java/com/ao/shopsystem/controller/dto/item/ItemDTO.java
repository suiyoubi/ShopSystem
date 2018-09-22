package com.ao.shopsystem.controller.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for the {@link com.ao.shopsystem.entity.Item}
 * Created by ao on 2018-09-21
 */
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private String name;

    private Long price;

    private String description;
}