package com.bruno.carlisting.dtos.request.car;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
public class CarUserRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "There must be a user responsible for the car")
    @Positive(message = "Role ID must be a positive integer")
    private Long userId;
}
