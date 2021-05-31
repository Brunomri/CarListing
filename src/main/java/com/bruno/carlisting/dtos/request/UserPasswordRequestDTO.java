package com.bruno.carlisting.dtos.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserPasswordRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 10, max = 25, message = "The password must have between 10 and 25 characters")
    private String password;
}
