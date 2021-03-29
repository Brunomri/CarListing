package com.bruno.carlisting.domain;

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
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;

    private String stateName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
    private List<Location> stateLocations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateOfCity")
    private List<City> citiesInState = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country countryOfState;

    public State() {
    }

    public State(String stateName, Country countryOfState) {
        this.stateName = stateName;
        this.countryOfState = countryOfState;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<Location> getStateLocations() {
        return stateLocations;
    }

    public void setStateLocations(List<Location> stateLocations) {
        this.stateLocations = stateLocations;
    }

    public List<City> getCitiesInState() {
        return citiesInState;
    }

    public void setCitiesInState(List<City> citiesInState) {
        this.citiesInState = citiesInState;
    }

    public Country getCountryOfState() {
        return countryOfState;
    }

    public void setCountryOfState(Country countryOfState) {
        this.countryOfState = countryOfState;
    }

}
