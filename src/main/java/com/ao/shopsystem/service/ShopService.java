package com.ao.shopsystem.service;

import com.ao.shopsystem.controller.dto.shop.ShopRequestDto;
import com.ao.shopsystem.entity.Shop;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.repository.ShopRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Service for Shop
 * Created by ao on 2018-09-22
 */
@Slf4j
@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /**
     * create a {@link Shop}
     *
     * @param shopRequestDto the Dto of the shop
     * @return the created entity
     */
    public Shop create(ShopRequestDto shopRequestDto) {

        log.info(
                "creating shop - name: {} address: {}",
                shopRequestDto.getName(),
                shopRequestDto.getAddress()
        );

        Shop shop = new Shop();

        shop.setName(shopRequestDto.getName());
        shop.setAddress(shopRequestDto.getAddress());

        return this.shopRepository.save(shop);
    }

    /**
     * find {@link Shop} by its Id (Primary Key)
     *
     * @param id the id of the shop
     * @return the targeted entity
     * @throws NotFoundException if no such entity being found
     */
    public Shop findById(Long id) throws NotFoundException {

        log.info("trying to find the shop with id {}", id);

        Optional<Shop> shopOptional = this.shopRepository.findById(id).filter(
                shop -> Objects.isNull(shop.getDeletedAt())
        );

        if (shopOptional.isPresent()) {
            return shopOptional.get();
        }

        log.error("No shop found with id {}", id);
        throw new NotFoundException("Shop is not found");
    }

    /**
     * soft delete the shop by its id
     *
     * @param id the id of the shop
     * @throws NotFoundException if no such entity being found
     */
    public void delete(Long id) throws NotFoundException {

        log.info("Deleting product with id {}", id);

        Optional<Shop> item = this.shopRepository.findById(id);

        if (!item.isPresent()) {
            log.error("No shop found with id {}", id);
            throw new NotFoundException("Shop is not found");
        }

        item.get().setDeletedAt(ZonedDateTime.now());

        this.shopRepository.save(item.get());

        log.info("Soft-Deleted product with id {}", id);
    }

    /**
     * update the address field in the {@link Shop}
     *
     * @param shopId the id of the shop
     * @param newAddress new address that replaces the original address
     * @return the updated shop entity
     * @throws NotFoundException if no such shop being found
     */
    public Shop updateAddress(Long shopId, String newAddress) throws NotFoundException {

        log.info("updating the address of shop: {}", shopId);

        Shop shop = this.findById(shopId);

        shop.setAddress(newAddress);

        return this.shopRepository.save(shop);
    }

    /**
     * retrieve all the shop entities
     *
     * @return list of all the shops
     */
    public List<Shop> getAddShops() {

        log.info("retrieving all the shops");

        return this.shopRepository.findAll().stream().filter(
                shop -> Objects.isNull(shop.getDeletedAt())
        ).collect(Collectors.toList());
    }
}
