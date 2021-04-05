package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;

    }

    @Override
    public Page<Role> getAllRoles(int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        return roleRepository.findAll(pageRequest);

    }

    @Override
    public Role createRole(Role newRole) {

        return roleRepository.save(newRole);

    }

    @Override
    public Role getRoleById(Integer roleId) {

        Optional<Role> role = roleRepository.findById(roleId);
        return role.orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + roleId));

    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {

        List<Role> userRoles = new ArrayList<>();
        userRoles.addAll(roleRepository.findByUsers_UserId(userId));
        return userRoles;

    }

    @Override
    public void deleteRoles(Integer roleId) {

        roleRepository.deleteById(roleId);

    }
}
