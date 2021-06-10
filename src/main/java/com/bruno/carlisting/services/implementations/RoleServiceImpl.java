package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.exceptions.entityRelationshipIntegrityException;
import com.bruno.carlisting.repositories.RoleRepository;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.RoleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ID_NOT_FOUND = "Role ID %s not found";
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
        return role.orElseThrow(() -> new ObjectNotFoundException(String.format(ROLE_ID_NOT_FOUND, roleId)));
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
    public Role updateRole(Role updatedRole, Integer roleId) {

        var optionalRole = roleRepository.findById(roleId);
        var currentRole = optionalRole.orElseThrow(() -> new ObjectNotFoundException(
                String.format(ROLE_ID_NOT_FOUND, roleId)));

        currentRole.setType(updatedRole.getType());

        try {
            return roleRepository.save(currentRole);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    "This role already exists:" +
                    " Type: %s", currentRole.getType()
            ));
        }
    }

    @Override
    public void deleteRoles(Integer roleId) {

        var roleToDelete = getRoleById(roleId);
        roleRepository.delete(roleToDelete);
    }
}
