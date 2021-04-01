package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Role;
import org.springframework.data.domain.Page;

public interface RoleService {

    Page<Role> getAllRoles(int page, int size);

    Role createRole(Role newRole);

    Role getRoleByUserId(Long userId);

}
