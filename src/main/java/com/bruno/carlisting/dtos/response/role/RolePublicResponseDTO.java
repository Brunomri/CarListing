package com.bruno.carlisting.dtos.response.role;

import com.bruno.carlisting.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class RolePublicResponseDTO {

    private static final long serialVersionUID = 1L;

    private String type;

    public static RolePublicResponseDTO toRolePublicResponseDTO(Role role) {
        return new RolePublicResponseDTO(role.getType());
    }

    public static Page<RolePublicResponseDTO> toRolePublicResponseDTO(Page<Role> rolesPage) {
        List<RolePublicResponseDTO> rolesListDTO = new ArrayList<>();
        rolesPage.forEach(role -> {
            var roleDTO = new RolePublicResponseDTO(role.getType());
            rolesListDTO.add(roleDTO);
        });
        return new PageImpl<>(rolesListDTO, rolesPage.getPageable(), rolesPage.getTotalElements());
    }

    public static List<RolePublicResponseDTO> toRolePublicResponseDTO(List<Role> rolesList) {
        List<RolePublicResponseDTO> rolesListDTO = new ArrayList<>();
        rolesList.forEach(role -> {
            var rolePublicResponseDTO = new RolePublicResponseDTO(role.getType());
            rolesListDTO.add(rolePublicResponseDTO);
        });
        return rolesListDTO;
    }
}
