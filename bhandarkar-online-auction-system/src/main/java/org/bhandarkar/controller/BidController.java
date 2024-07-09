package org.bhandarkar.controller;

import org.bhandarkar.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    @Autowired
    BidService bidService;

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<String> placeBid(@PathVariable Long auctionId, @RequestParam double bidAmount, @RequestParam long userId) {
        try {
            bidService.processBidWithRetry(auctionId, bidAmount, userId);
            return ResponseEntity.ok("Bid placed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @KafkaListener(topics = "bids", groupId = "auction-group")
//    public void consumeBid(String message) {
//        String[] parts = message.split(",");
//        UUID auctionId = UUID.fromString(parts[0]);
//        double bidAmount = Double.parseDouble(parts[1]);
//        processBid(auctionId, bidAmount);
//    }

}
