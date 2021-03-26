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
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;

    private Integer countryId;

    private String stateName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
    private List<Location> stateLocations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateOfCity")
    private List<City> citiesInState = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "stateToCountry_id")
    private Country countryOfState;

    public State() {
    }

    public State(Integer countryId, String stateName) {
        this.countryId = countryId;
        this.stateName = stateName;
    }
}
