package com.bruno.carlisting.dtos.response.role;

import com.bruno.carlisting.domain.Role;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RolePrivateResponseDTO extends RolePublicResponseDTO {

    private Integer roleId;
    private List<Long> usersIds;

    public RolePrivateResponseDTO(String type, Integer roleId, List<Long> usersIds) {
        super(type);
        this.roleId = roleId;
        this.usersIds = usersIds;
    }

    public static RolePrivateResponseDTO toRolePrivateResponseDTO(Role role) {
        List<Long> usersIds = new ArrayList<>();
        role.getUsers().forEach(user -> usersIds.add(user.getUserId()));
        return new RolePrivateResponseDTO(role.getType(), role.getRoleId(), usersIds);
    }
}
