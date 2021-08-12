package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.dtos.request.role.RoleRequestDTO;
import com.bruno.carlisting.dtos.response.role.RolePrivateResponseDTO;
import com.bruno.carlisting.dtos.response.role.RolePublicResponseDTO;
import com.bruno.carlisting.services.interfaces.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/roles")
@Validated
@Slf4j
public class RoleController {

    private static final String ROLE_PAGE_DEFAULT_NUMBER = "0";
    private static final String ROLE_PAGE_DEFAULT_SIZE = "1";
    private static final int ROLE_PAGE_MIN_NUMBER = 0;
    private static final int ROLE_PAGE_MIN_SIZE = 1;
    private static final int ROLE_PAGE_MAX_SIZE = 10;

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "Return all roles grouped in pages")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a page of roles"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page content not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Page<RolePublicResponseDTO>> findAllRoles(

        @RequestParam(value = "page", required = false, defaultValue = ROLE_PAGE_DEFAULT_NUMBER)
        @Min(value = ROLE_PAGE_MIN_NUMBER,
            message = "Page number must be greater than or equal to " + ROLE_PAGE_MIN_NUMBER) int page,

        @RequestParam(value = "size", required = false, defaultValue = ROLE_PAGE_DEFAULT_SIZE)
        @Min(value = ROLE_PAGE_MIN_SIZE,
            message = "Page size must be greater than or equal to " + ROLE_PAGE_MIN_SIZE)
        @Max(value = ROLE_PAGE_MAX_SIZE,
            message = "Page size must be less than or equal to " + ROLE_PAGE_MAX_SIZE) int size) {

        log.info("Finding all roles on page {} with maximum size {}", page, size);

        var rolesPageDTO = RolePublicResponseDTO.toRolePublicResponseDTO(
                roleService.getAllRoles(page, size));

        log.info("Returning {} roles on page {}",
                rolesPageDTO.getContent().size(), rolesPageDTO.getPageable().getPageNumber());

        return ResponseEntity.ok().body(rolesPageDTO);
    }

    @ApiOperation(value = "Find a role by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return the role with corresponding ID"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Role not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/{roleId}", produces = "application/json")
    public ResponseEntity<RolePublicResponseDTO> findRoleById(

        @PathVariable @Positive(message = "Role ID must be a positive integer") Integer roleId) {

        log.info("Finding role by ID = {}", roleId);

        var role = roleService.getRoleById(roleId);

        log.info("Returning role of ID = {}", roleId);

        return ResponseEntity.ok().body(RolePublicResponseDTO.toRolePublicResponseDTO(role));
    }

    @ApiOperation(value = "Find all roles for a user ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return the role with corresponding ID"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Role not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<List<RolePublicResponseDTO>> findRolesByUserId(

        @PathVariable @Positive(message = "User ID must be a positive integer") Long userId) {

        log.info("Finding all roles assigned to user ID = {}", userId);

        List<Role> userRoles = roleService.getRolesByUserId(userId);

        log.info("Returning all roles assigned to user ID = {}", userId);

        return ResponseEntity.ok().body(RolePublicResponseDTO.toRolePublicResponseDTO(userRoles));
    }

    @ApiOperation(value = "Add a new role")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "New role created"),
        @ApiResponse(code = 400, message = "Invalid Role data provided"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RolePrivateResponseDTO> createRole(

        @Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        log.info("Creating role: type = {}", roleRequestDTO.getType());

        var newRole = roleService.createRole(roleRequestDTO.toRole());
        var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles/{roleId}").
                buildAndExpand(newRole.getRoleId()).toUri();

        log.info("Returning created role: roleId = {}, type = {}", newRole.getRoleId(), newRole.getType());

        return ResponseEntity.created(uri).body(RolePrivateResponseDTO.toRolePrivateResponseDTO(newRole));
    }

    @ApiOperation(value = "Update an existing role")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Role updated"),
        @ApiResponse(code = 400, message = "Invalid Role data provided"),
        @ApiResponse(code = 404, message = "Role not found"),
        @ApiResponse(code = 500, message = "Server exception")
    })
    @PutMapping(value = "/{roleId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RolePrivateResponseDTO> updateRole(

            @PathVariable @Positive(message = "Role ID must be a positive integer") Integer roleId,

            @Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        log.info("Updating role: type = {}", roleRequestDTO.getType());

        var updatedRole = roleService.updateRole(roleRequestDTO.toRole(), roleId);

        log.info("Returning updated role: roleId = {}, type = {}", updatedRole.getRoleId(), updatedRole.getType());

        return ResponseEntity.ok().body(RolePrivateResponseDTO.toRolePrivateResponseDTO(updatedRole));
    }

    @ApiOperation(value = "Delete an existing role")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Role deleted"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @DeleteMapping(value = "/{roleId}")
    public ResponseEntity<Void> deleteRole(

        @PathVariable @Positive(message = "Role ID must be a positive integer") Integer roleId) {

        log.info("Deleting role ID = {}", roleId);

        roleService.deleteRoles(roleId);

        log.info("Deleted role ID = {}", roleId);

        return ResponseEntity.noContent().build();
    }
}
