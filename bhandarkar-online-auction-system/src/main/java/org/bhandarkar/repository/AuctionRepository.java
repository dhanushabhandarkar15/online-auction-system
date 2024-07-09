package org.bhandarkar.repository;


import org.bhandarkar.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
}
