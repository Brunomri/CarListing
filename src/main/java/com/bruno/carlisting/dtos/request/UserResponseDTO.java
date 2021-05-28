package com.bruno.carlisting.dtos.request;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponseDTO {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String displayName;
    private String contact;

    private List<Role> roles;

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUsername(), user.getDisplayName(),
                user.getContact(), user.getRoles());
    }
}
