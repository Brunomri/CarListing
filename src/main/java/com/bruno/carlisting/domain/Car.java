package com.bruno.carlisting.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private Long userId;

    private String make;

    private String model;

    private String year;

    private String trim;

    private String color;

    private String transmission;

    private String fuel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<Listing> carListings = new ArrayList<>();

    public Car() {
    }

    public Car(Long userId, String make, String model, String year, String trim, String color, String transmission,
               String fuel) {
        this.userId = userId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.trim = trim;
        this.color = color;
        this.transmission = transmission;
        this.fuel = fuel;
    }

}
