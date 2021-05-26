package com.bruno.carlisting.domain;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long cityId;

    @NotBlank(message = "City name is mandatory")
    @Size(min = 2, max = 255, message = "City name must have between 2 and 255 characters")
    private String cityName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Location> cityLocations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State stateOfCity;

    public City(String cityName, State stateOfCity) {
        this.cityName = cityName;
        this.stateOfCity = stateOfCity;
    }
}
