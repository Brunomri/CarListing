package com.bruno.carlisting.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    private String countryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<Location> countryLocations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryOfState")
    private List<State> statesInCountry = new ArrayList<>();

    public Country() {
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<Location> getCountryLocations() {
        return countryLocations;
    }

    public void setCountryLocations(List<Location> countryLocations) {
        this.countryLocations = countryLocations;
    }

    public List<State> getStatesInCountry() {
        return statesInCountry;
    }

    public void setStatesInCountry(List<State> statesInCountry) {
        this.statesInCountry = statesInCountry;
    }

}
