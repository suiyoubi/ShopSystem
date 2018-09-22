package com.ao.shopsystem.controller;

import com.ao.shopsystem.controller.dto.item.ItemDTO;
import com.ao.shopsystem.entity.Item;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.service.ItemService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of the {@link Item}
 * Created by ao on 2018-09-21
 */

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private static final String NEW_API_CALL = "NEW API Call: ";
    private static final String FAILURE_MESSAGE = "Failure: ";
    private static final String SUCCESS_MESSAGE = "Success: ";

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ItemDTO getItem(@PathVariable Long itemId) throws NotFoundException {

        log.info(NEW_API_CALL + "Trying to fetch item with id {}", itemId);

        Item item = this.itemService.getById(itemId);

        if (Objects.isNull(item)) {
            log.error(FAILURE_MESSAGE + "No item found with id {}", itemId);

            throw new NotFoundException("No item found with id");
        }

        log.info(SUCCESS_MESSAGE + "Fetch the item with id {}", itemId);

        return ItemController.convertModel(item);
    }

    @PostMapping
    public ItemDTO createItem(@RequestBody ItemDTO itemDTO) {

        log.info(NEW_API_CALL);

        Item item = this.itemService.create(itemDTO);

        return ItemController.convertModel(item);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {

        log.info(NEW_API_CALL);

        this.itemService.delete(itemId);
    }

    static ItemDTO convertModel(Item item) {
        return ItemDTO.builder()
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .build();
    }

    private static Item convertToEntity(ItemDTO itemDTO) {

        Item item = new Item();

        item.setPrice(item.getPrice());
        item.setName(item.getName());
        item.setDescription(item.getDescription());

        return item;
    }
}
