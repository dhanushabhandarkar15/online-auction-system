package org.bhandarkar.service;

import jakarta.transaction.Transactional;
import org.bhandarkar.entity.AuctionEntity;
import org.bhandarkar.entity.BidEntity;
import org.bhandarkar.entity.UserEntity;
import org.bhandarkar.repository.AuctionRepository;
import org.bhandarkar.repository.BidRepository;
import org.bhandarkar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidService {

    private static final int MAX_RETRIES = 3;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Retryable(maxAttempts = MAX_RETRIES, include = {ObjectOptimisticLockingFailureException.class},
            backoff = @Backoff(delay = 100, maxDelay = 500))
    @Transactional
    public void processBidWithRetry(Long auctionId, double bidAmount, long userId) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("AuctionItem not found"));
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (bidAmount > auctionEntity.getCurrentHighestBid()) {
            auctionEntity.setCurrentHighestBid(bidAmount);
            auctionRepository.save(auctionEntity);
            saveBid(auctionEntity, bidAmount, userEntity.get());
        }
    }

    @Transactional
    public void saveBid(AuctionEntity auctionEntity, double bidAmount, UserEntity userEntity) {
        BidEntity bidEntity = BidEntity.builder().auction(auctionEntity).amount(bidAmount).user(userEntity).build();
        bidRepository.save(bidEntity);
    }

}
