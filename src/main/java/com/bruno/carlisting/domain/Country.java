package com.bruno.carlisting.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer countryId;

    @NotBlank(message = "Country name is mandatory")
    @Size(min = 2, max = 255, message = "Country name must have between 2 and 255 characters")
    @Column(unique = true)
    private String countryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<Location> countryLocations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryOfState")
    private List<State> statesInCountry = new ArrayList<>();

    public Country(String countryName) {
        this.countryName = countryName;
    }
}
