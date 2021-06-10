package com.bruno.carlisting.dtos.response.role;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RolePrivateResponseDTO {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String type;
    private List<User> users;

    public static RolePrivateResponseDTO toRolePrivateResponseDTO(Role role) {
        return new RolePrivateResponseDTO(role.getRoleId(), role.getType(), role.getUsers());
    }
}
