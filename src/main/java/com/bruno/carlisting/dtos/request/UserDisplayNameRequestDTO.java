package com.bruno.carlisting.dtos.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserDisplayNameRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Display name is mandatory")
    @Size(min = 3, max = 30, message = "The display name must have between 3 and 30 characters")
    private String displayName;
}
