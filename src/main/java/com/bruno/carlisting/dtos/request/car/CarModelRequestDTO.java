package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CarModelRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Model is mandatory")
    @Size(max = 30, message = "Model must have 30 characters or less")
    private String model;
}
