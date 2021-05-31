package com.bruno.carlisting.dtos.request.user;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class UserContactRequestDTO {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "^[\\w.-]+@[\\w-]+[.]com$",
            message = "Contact must be an e-mail address in format: localpart@domain.com")
    private String contact;
}
