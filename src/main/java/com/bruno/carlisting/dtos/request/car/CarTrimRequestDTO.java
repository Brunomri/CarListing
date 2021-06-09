package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CarTrimRequestDTO {

    @NotBlank(message = "Trim is mandatory")
    @Size(max = 30, message = "Trim must have 30 characters or less")
    private String trim;
}
