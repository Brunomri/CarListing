package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    Page<Role> getAllRoles(int page, int size);

    Role createRole(Role newRole);

    Role getRoleById(Integer roleId);

    List<Role> getRolesByUserId(Long userId);

    void deleteRoles(Integer roleId);

}
