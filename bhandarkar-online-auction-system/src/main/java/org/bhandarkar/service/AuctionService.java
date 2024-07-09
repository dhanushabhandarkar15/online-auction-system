package org.bhandarkar.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bhandarkar.entity.AuctionEntity;
import org.bhandarkar.entity.ItemEntity;
import org.bhandarkar.repository.AuctionRepository;
import org.bhandarkar.repository.BidRepository;
import org.bhandarkar.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuctionService {


    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BidRepository bidRepository;


    @Transactional
    public AuctionEntity startAuction(Long itemId, LocalDateTime startTime, LocalDateTime endTime) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item with id " + itemId + " not found"));

        AuctionEntity auctionEntity = AuctionEntity.builder().startTime(startTime).endTime(endTime).currentHighestBid(0).build();
        auctionEntity.setItem(item);

        return auctionRepository.save(auctionEntity);
    }

    @Transactional
    public AuctionEntity endAuction(Long auctionId) {
        AuctionEntity auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction with id " + auctionId + " not found"));

        if (auction.getEndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Auction with id " + auctionId + " has already ended");
        }

        Double currentHighestBid = bidRepository.findHighestBidForAuction(auctionId).getAmount();
        auction.setCurrentHighestBid(currentHighestBid);

        if (currentHighestBid.compareTo(auction.getItem().getReservedPrice()) >= 0) {
            auction.setStatus("SUCCESSFUL");
        } else {
            auction.setStatus("NOT SUCCESS");
        }
        return auctionRepository.save(auction);
    }

    public String getCurrentAuctionStatus(Long auctionId) {
        AuctionEntity auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction with id " + auctionId + " not found"));
        Double currentHighestBid = bidRepository.findHighestBidForAuction(auctionId).getAmount();
        if (currentHighestBid.compareTo(auction.getItem().getReservedPrice()) >= 0) {
            return "SUCCESSFUL";
        } else {
            return "NOT SUCCESS";
        }
    }


}
