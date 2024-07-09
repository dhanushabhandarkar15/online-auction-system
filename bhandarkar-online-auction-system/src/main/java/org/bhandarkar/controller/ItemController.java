package org.bhandarkar.controller;

import org.bhandarkar.entity.ItemEntity;
import org.bhandarkar.model.Item;
import org.bhandarkar.model.User;
import org.bhandarkar.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {


    @Autowired
    ItemService itemService;

    @PostMapping("/create/item")
    @PreAuthorize("hasRole('AUCTIONEER')")
    public ResponseEntity<?> createItem(@RequestBody Item itemRequest, User currentUser) {

        ItemEntity itemEntity = itemService.saveItem(itemRequest, currentUser);
        return new ResponseEntity<>("\"Auction item created successfully for " + itemEntity.getId(), HttpStatus.OK);
    }

    // Endpoint to get all auction items
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('AUCTIONEER', 'PARTICIPANT', 'ADMIN')")
    public ResponseEntity<?> getAllItems() {
        List<ItemEntity> itemEntities = itemService.getAllItems();
        return ResponseEntity.ok(itemEntities);
    }
}
