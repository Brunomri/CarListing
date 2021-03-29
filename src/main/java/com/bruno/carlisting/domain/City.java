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
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    private String cityName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Location> cityLocations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State stateOfCity;

    public City() {
    }

    public City(String cityName, State stateOfCity) {
        this.cityName = cityName;
        this.stateOfCity = stateOfCity;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Location> getCityLocations() {
        return cityLocations;
    }

    public void setCityLocations(List<Location> cityLocations) {
        this.cityLocations = cityLocations;
    }

    public State getStateOfCity() {
        return stateOfCity;
    }

    public void setStateOfCity(State stateOfCity) {
        this.stateOfCity = stateOfCity;
    }

}
