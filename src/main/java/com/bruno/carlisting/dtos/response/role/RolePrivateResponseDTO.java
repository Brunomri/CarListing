package com.bruno.carlisting.dtos.response.role;

import com.bruno.carlisting.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RolePrivateResponseDTO {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String type;
    private List<Long> usersIds;

    public static RolePrivateResponseDTO toRolePrivateResponseDTO(Role role) {
        List<Long> usersIds = new ArrayList<>();
        role.getUsers().forEach(user -> usersIds.add(user.getUserId()));
        return new RolePrivateResponseDTO(role.getRoleId(), role.getType(), usersIds);
    }
}
