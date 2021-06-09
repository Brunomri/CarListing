package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CarYearRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Year is mandatory")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 9999, message = "Year must have 4 digits")
    private Integer year;
}
