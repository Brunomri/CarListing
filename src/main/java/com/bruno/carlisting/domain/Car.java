package com.bruno.carlisting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"make", "model", "year", "trim"})
})
@Getter
@Setter
@NoArgsConstructor
public class Car {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long carId;

    private String make;

    private String model;

    private String year;

    private String trim;

    private String color;

    private String transmission;

    private String fuel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<Listing> carListings = new ArrayList<>();

    public Car(String make, String model, String year, String trim, String color, String transmission,
               String fuel) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.trim = trim;
        this.color = color;
        this.transmission = transmission;
        this.fuel = fuel;
    }
}
