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
    private final PagingService pagingService;

    public RoleServiceImpl(RoleRepository roleRepository, PagingService pagingService) {

        this.roleRepository = roleRepository;
        this.pagingService = pagingService;
    }

    @Override
    public Page<Role> getAllRoles(int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Role> rolesPage = roleRepository.findAll(pageRequest);
        pagingService.validatePage(rolesPage);
        return rolesPage;
    }

    @Override
    public Role getRoleById(Integer roleId) {

        Optional<Role> role = roleRepository.findById(roleId);
        return role.orElseThrow(() -> new ObjectNotFoundException("Role not found! Id: " + roleId));
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {

        List<Role> userRoles = new ArrayList<>();
        userRoles.addAll(roleRepository.findByUsers_UserId(userId));
        if (userRoles.isEmpty()) throw new
                ObjectNotFoundException("There are no roles associated with this user! Id: " + userId);
        return userRoles;
    }

    @Override
    public Role createRole(Role newRole) {

        return roleRepository.save(newRole);
    }

    @Override
    public void deleteRoles(Integer roleId) {

        Optional<Role> roleToDelete = roleRepository.findById(roleId);
        roleRepository.delete(roleToDelete.orElseThrow(
                () -> new ObjectNotFoundException("Role not found! Id: " + roleId)));
    }
}
