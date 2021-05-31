package com.bruno.carlisting.dtos.response.user;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserAdminResponseDTO {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String displayName;
    private String contact;

    private List<Role> roles;

    public static UserAdminResponseDTO toDTO(User user) {
        return new UserAdminResponseDTO(user.getUserId(), user.getUsername(),
                user.getDisplayName(), user.getContact(), user.getRoles());
    }
}
