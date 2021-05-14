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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer stateId;

    private String stateName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
    private List<Location> stateLocations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateOfCity")
    private List<City> citiesInState = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country countryOfState;

    public State(String stateName, Country countryOfState) {
        this.stateName = stateName;
        this.countryOfState = countryOfState;
    }
}
