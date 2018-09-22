package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.shop.ShopRequestDto;
import com.ao.shopsystem.controller.dto.shop.ShopResponseDto;
import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.ShopService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for {@link Shop}
 * Created by ao on 2018-09-22
 */
@Slf4j
@RestController
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ShopResponseDto createShop(@RequestBody ShopRequestDto shopRequestDto) {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Shop shop = this.shopService.create(shopRequestDto);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "created the shop with id {}", shop.getId());

        return ShopController.convertModel(shop);
    }

    @GetMapping("/{shopId}")
    public ShopResponseDto getShop(@PathVariable Long shopId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Shop shop = this.shopService.findById(shopId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "Fetch the shop with id {}", shopId);

        return ShopController.convertModel(shop);
    }

    @DeleteMapping("/{shopId}")
    public void deleteShop(@PathVariable Long shopId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        this.shopService.delete(shopId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "deleted the shop with id {}", shopId);

    }

    static ShopResponseDto convertModel(Shop shop) {

        if (Objects.isNull(shop)) {
            return null;
        }

        return ShopResponseDto.builder()
                .shopId(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .build();
    }
}
