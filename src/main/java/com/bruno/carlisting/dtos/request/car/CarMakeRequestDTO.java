package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CarMakeRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Make is mandatory")
    @Size(max = 30, message = "Make must have 30 characters or less")
    private String make;
}
