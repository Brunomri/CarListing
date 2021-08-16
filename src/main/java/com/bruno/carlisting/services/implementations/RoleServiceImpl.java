package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.exceptions.entityRelationshipIntegrityException;
import com.bruno.carlisting.repositories.RoleRepository;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ID_NOT_FOUND = "Role ID %s not found";
    public static final String PAGE_HAS_NO_ROLES = "Page %s has no roles";
    public static final String ROLE_ALREADY_EXISTS = "Role %s already exists";
    public static final String NO_ROLES_ASSOCIATED_TO_USER = "There are no roles associated to user ID %s";

    private final RoleRepository roleRepository;
    private final PagingService pagingService;

    public RoleServiceImpl(RoleRepository roleRepository, PagingService pagingService) {
        this.roleRepository = roleRepository;
        this.pagingService = pagingService;
    }

    @Override
    public Page<Role> getAllRoles(int page, int size) {

        var pageRequest = PageRequest.of(page, size);
        var rolesPage = roleRepository.findAll(pageRequest);

        log.debug("method = getAllRoles, page number = {}, page size = {}, rolesPage = {}",
                rolesPage.getPageable().getPageNumber(), rolesPage.getPageable().getPageSize(), rolesPage.getContent());

        pagingService.validatePage(rolesPage, String.format(PAGE_HAS_NO_ROLES, page));
        return rolesPage;
    }

    @Override
    public Role getRoleById(Integer roleId) {

        var role = roleRepository.findById(roleId);

        log.debug("method = getRoleById, role = {}", role);

        return role.orElseThrow(() -> new ObjectNotFoundException(String.format(ROLE_ID_NOT_FOUND, roleId)));
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {

        List<Role> userRoles = new ArrayList<>(roleRepository.findByUsers_UserId(userId));

        log.debug("method = getRolesByUserId, userRoles = {}", userRoles);

        if (userRoles.isEmpty()) {

            log.warn("Object not found exception occurred:");

            throw new ObjectNotFoundException(String.format(NO_ROLES_ASSOCIATED_TO_USER, userId));
        }
        return userRoles;
    }

    @Override
    public Role createRole(Role newRole) {

        try {

            log.debug("method = createRole, newRole = {}", newRole);

            return roleRepository.save(newRole);
        } catch (DataIntegrityViolationException e) {

            log.warn("Entity relationship integrity exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    ROLE_ALREADY_EXISTS, newRole.getType()));
        }
    }

    @Override
    public Role updateRole(Role updatedRole, Integer roleId) {

        var optionalRole = roleRepository.findById(roleId);
        var currentRole = optionalRole.orElseThrow(() -> new ObjectNotFoundException(
                String.format(ROLE_ID_NOT_FOUND, roleId)));

        log.debug("method = updateRole, currentRole = {}", currentRole);

        currentRole.setType(updatedRole.getType());

        log.debug("method = updateRole, currentRole = {}", currentRole);

        try {
            return roleRepository.save(currentRole);
        } catch (DataIntegrityViolationException e) {

            log.warn("Entity relationship integrity exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    ROLE_ALREADY_EXISTS, currentRole.getType()
            ));
        }
    }

    @Override
    public void deleteRoles(Integer roleId) {

        var roleToDelete = roleRepository.findById(roleId);

        log.debug("method = deleteRoles, roleToDelete = {}", roleToDelete);

        roleRepository.delete(roleToDelete.orElseThrow(() -> new ObjectNotFoundException(
                String.format(ROLE_ID_NOT_FOUND, roleId))));
    }
}
