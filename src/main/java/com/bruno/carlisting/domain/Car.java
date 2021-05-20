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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Make is mandatory")
    @Size(max = 30, message = "Make must have 30 characters or less")
    private String make;

    @NotBlank(message = "Model is mandatory")
    @Size(max = 30, message = "Model must have 30 characters or less")
    private String model;

    @NotNull(message = "Year is mandatory")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 9999, message = "Year must have 4 digits")
    private Integer year;

    @NotBlank(message = "Trim is mandatory")
    @Size(max = 30, message = "Trim must have 30 characters or less")
    private String trim;

    @NotBlank(message = "Color is mandatory")
    @Size(max = 30, message = "Color must have 30 characters or less")
    private String color;

    @Pattern(regexp = "^(AT|MT)$")
    private String transmission;

    @Pattern(regexp = "^(Gasoline|Ethanol|Flex-Fuel|Electricity|Hybrid)$")
    private String fuel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<Listing> carListings = new ArrayList<>();

    public Car(String make, String model, Integer year, String trim, String color, String transmission,
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
