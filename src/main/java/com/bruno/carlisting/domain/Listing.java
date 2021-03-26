package com.bruno.carlisting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    private Long cardId;

    private Long userId;

    private Integer price;

    private Integer mileage;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Listing() {
    }

    public Listing(Long cardId, Long userId, Integer price, Integer mileage, String description) {
        this.cardId = cardId;
        this.userId = userId;
        this.price = price;
        this.mileage = mileage;
        this.description = description;
    }
}
