package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class CarFuelRequestDTO {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "^(Gasoline|Ethanol|Flex-Fuel|Electricity|Hybrid)$",
            message = "Fuel must be one of the following: Gasoline, Ethanol, Flex-Fuel, Electricity or Hybrid")
    private String fuel;
}
