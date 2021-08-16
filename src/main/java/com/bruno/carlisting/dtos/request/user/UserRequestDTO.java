package com.bruno.carlisting.dtos.request.user;

import com.bruno.carlisting.domain.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 30, message = "Username must have 30 characters or less")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 10, max = 25, message = "The password must have between 10 and 25 characters")
    private String password;

    @NotBlank(message = "Display name is mandatory")
    @Size(min = 3, max = 30, message = "The display name must have between 3 and 30 characters")
    private String displayName;

    @Pattern(regexp = "^[\\w.-]+@[\\w-]+[.]com$",
            message = "Contact must be an e-mail address in format: localpart@domain.com")
    private String contact;

    @NotEmpty(message = "User must have at least 1 role")
    private List<@Positive(message = "Role ID must be a positive integer") Integer> rolesIds = new ArrayList<>();

    public User toUser() {
        return new User(username, password, displayName, contact);
    }

    @Override
    public String toString() {
        return String.format(
                "\nusername = %s\n" +
                "displayName = %s\n" +
                "contact = %s\n",
                this.getUsername(), this.getDisplayName(), this.getContact());
    }
}
