package org.bhandarkar.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {

    private String name;

    private String description;

    private Double reservedPrice;
}
