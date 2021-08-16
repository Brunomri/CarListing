package com.bruno.carlisting.dtos.request.role;

import com.bruno.carlisting.domain.Role;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class RoleRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Type is mandatory")
    @Size(min = 3, max = 20, message = "Type must have between 3 and 20 characters")
    private String type;

    public Role toRole() {
        return new Role(type);
    }

    @Override
    public String toString() {
        return String.format(
                "\ntype = %s\n",
                this.getType());
    }
}
