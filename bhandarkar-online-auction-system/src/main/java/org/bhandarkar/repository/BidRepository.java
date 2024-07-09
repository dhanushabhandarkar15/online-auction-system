package org.bhandarkar.repository;

import org.bhandarkar.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<BidEntity, Long> {
    List<BidEntity> findByAuctionIdOrderByBidAmountDesc(Long auctionId);

    BidEntity findHighestBidForAuction(Long auctionId);
}