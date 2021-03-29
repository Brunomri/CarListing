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

}
