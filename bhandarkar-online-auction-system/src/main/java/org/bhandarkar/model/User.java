package org.bhandarkar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private long userId;

    private String username;

    private String password;

    private String role; // Auctioneer, Participant, Administrator
}
