package com.bruno.carlisting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Listing {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long listingId;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be a positive integer")
    private Integer price;

    @NotNull(message = "Mileage is mandatory")
    @Positive(message = "Mileage must be a positive integer")
    private Integer mileage;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 1000, message = "Description must have between 10 and 1000 characters")
    private String description;

//    todo: A DTO for a listing response might avoid the @JsonIgnore
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne/*(fetch = FetchType.LAZY)*/
    @JoinColumn(name = "car_id")
    private Car car;

    public Listing(Integer price, Integer mileage, String description, User user, Car car) {
        this.price = price;
        this.mileage = mileage;
        this.description = description;
        this.user = user;
        this.car = car;
    }
}
