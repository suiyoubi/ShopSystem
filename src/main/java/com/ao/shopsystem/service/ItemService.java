package com.ao.shopsystem.service;

import com.ao.shopsystem.controller.dto.item.ItemDTO;
import com.ao.shopsystem.entity.Item;
import com.ao.shopsystem.exception.NotFoundException;
import com.ao.shopsystem.repository.ItemRepository;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service of {@link Item}
 * Created by ao on 2018-09-21
 */
@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(ItemDTO itemDTO) {

        log.info(
                "Adding a new item - name {} ({}) with price {}",
                itemDTO.getName(),
                itemDTO.getDescription(),
                itemDTO.getPrice()
        );

        Item item = new Item();

        item.setPrice(itemDTO.getPrice());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());

        return this.itemRepository.save(item);
    }

    public Item getById(Long id) throws NotFoundException {

        log.info("Trying to fetch item with id {}", id);

        Optional<Item> optionalItem = this.itemRepository.findById(id).filter(
                item -> Objects.isNull(item.getDeletedAt())
        );

        if (optionalItem.isPresent()) {
            return optionalItem.get();
        }

        log.error("No item found with id {}", id);
        throw new NotFoundException("Item is not found");
    }

    public void delete(Long id) {

        log.info("Deleting item with id {}", id);

        Optional<Item> item = this.itemRepository.findById(id);

        if (!item.isPresent()) {
            log.error("No item found with id {}", id);
        }

        item.get().setDeletedAt(ZonedDateTime.now());

        this.itemRepository.save(item.get());

        log.info("Soft-Deleted item with id {}", id);
    }
}
