package org.bhandarkar.controller;

import org.bhandarkar.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;


    @PostMapping("/start")
    public ResponseEntity<String> startAuction(@RequestParam long itemId, LocalDateTime startTime, LocalDateTime endTime) {
        auctionService.startAuction(itemId, startTime, endTime);
        return new ResponseEntity<>("Auction Started successfully", HttpStatus.OK);
    }

    @PostMapping("/end")
    public ResponseEntity<String> endAuction(@RequestParam Long auctionId) {
        auctionService.endAuction(auctionId);
        return new ResponseEntity<>("Auction ended successfully", HttpStatus.OK);
    }

    @GetMapping("/{auctionId}/status")
    public ResponseEntity<String> getAuctionStatus(@PathVariable Long auctionId) {
        String auctionStatus = auctionService.getCurrentAuctionStatus(auctionId);
        return new ResponseEntity<>("Auction status is " + auctionStatus, HttpStatus.OK);
    }

}