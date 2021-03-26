package com.bruno.carlisting.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Location {

    @Id
    private Long locationId;

    private Integer countryId;

    private Integer stateId;

    private Long cityId;

    @OneToOne
    @MapsId
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "locationToCountry_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "locationToState_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "locationToCity_id")
    private City city;

    public Location() {
    }

    public Location(Long listingId, Integer countryId, Integer stateId, Long cityId) {
        this.locationId = listingId;
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
    }
}
