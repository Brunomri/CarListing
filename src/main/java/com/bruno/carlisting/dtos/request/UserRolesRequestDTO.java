package com.bruno.carlisting.dtos.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserRolesRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "User must have at least 1 role")
    private List<@Positive(message = "Role ID must be a positive integer") Integer> rolesIds = new ArrayList<>();
}
