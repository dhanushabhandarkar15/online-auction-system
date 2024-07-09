package org.bhandarkar.service;

import org.bhandarkar.entity.ItemEntity;
import org.bhandarkar.entity.UserEntity;
import org.bhandarkar.model.Item;
import org.bhandarkar.model.User;
import org.bhandarkar.repository.ItemRepository;
import org.bhandarkar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public ItemEntity saveItem(Item itemRequest, User user) {
        // Retrieve the current auctioneer from database
        UserEntity auctioneer = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ItemEntity auctionItem = ItemEntity.builder()
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .reservedPrice(itemRequest.getReservedPrice())
                .auctioneer(auctioneer).build();
        return itemRepository.save(auctionItem);
    }


    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }
}
