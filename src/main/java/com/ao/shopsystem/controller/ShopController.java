package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.shop.ShopRequestDto;
import com.ao.shopsystem.controller.dto.shop.ShopResponseDto;
import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.ShopService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    /**
     * create the new shop entity
     *
     * @param shopRequestDto the request entity for shop
     * @return the response entity for shop
     */
    @PostMapping
    public ShopResponseDto createShop(@RequestBody ShopRequestDto shopRequestDto) {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Shop shop = this.shopService.create(shopRequestDto);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "created the shop with id {}", shop.getId());

        return ShopController.convertModel(shop);
    }

    /**
     * retrieve the shop with the given id
     *
     * @param shopId id of the shop entity
     * @return the response entity for shop
     * @throws NotFoundException if no such shop being found
     */
    @GetMapping("/{shopId}")
    public ShopResponseDto getShop(@PathVariable Long shopId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Shop shop = this.shopService.findById(shopId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "Fetch the shop with id {}", shopId);

        return ShopController.convertModel(shop);
    }

    /**
     * delete the shop with given id
     *
     * @param shopId id of the shop entity
     * @throws NotFoundException no such shop being found
     */
    @DeleteMapping("/{shopId}")
    public void deleteShop(@PathVariable Long shopId) throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        this.shopService.delete(shopId);

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "deleted the shop with id {}", shopId);

    }

    /**
     * update the address of a specified shop
     *
     * @param shopId id of the shop entity
     * @param newAddress new address
     * @return updated response entity for shop
     * @throws NotFoundException no such shop being found
     */
    @PatchMapping("/{shopId}/address")
    public ShopResponseDto changeAddress(@PathVariable Long shopId, @RequestBody String newAddress)
            throws NotFoundException {

        log.info(ControllerLogHelper.NEW_API_CALL);

        Shop shop = this.shopService.updateAddress(shopId, newAddress);

        log.info(
                ControllerLogHelper.SUCCESS_MESSAGE + "updated the shop {} with new address: {}",
                shopId,
                newAddress
        );

        return ShopController.convertModel(shop);
    }

    /**
     * retrieve all the shops
     *
     * @return list of all the response entity of shops
     */
    @GetMapping("/all")
    public List<ShopResponseDto> getAllShops() {

        log.info(ControllerLogHelper.NEW_API_CALL);

        List<Shop> shops = this.shopService.getAddShops();

        log.info(ControllerLogHelper.SUCCESS_MESSAGE + "retrieved all the shops");

        return shops.stream().map(ShopController::convertModel).collect(Collectors.toList());
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
