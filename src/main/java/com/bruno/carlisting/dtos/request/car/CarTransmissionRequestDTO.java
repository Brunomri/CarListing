package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class CarTransmissionRequestDTO {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "^(AT|MT)$",
            message = "Transmission must be either AT for automatic transmission or MT for manual transmission")
    private String transmission;
}
