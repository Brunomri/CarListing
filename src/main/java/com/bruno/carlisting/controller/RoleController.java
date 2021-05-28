package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.services.interfaces.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/roles")
@Validated
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
    public ResponseEntity<Page<Role>> findAllRoles(

        @RequestParam(value = "page", required = false, defaultValue = ROLE_PAGE_DEFAULT_NUMBER)
        @Min(value = ROLE_PAGE_MIN_NUMBER,
            message = "Page number must be greater than or equal to " + ROLE_PAGE_MIN_NUMBER) int page,

        @RequestParam(value = "size", required = false, defaultValue = ROLE_PAGE_DEFAULT_SIZE)
        @Min(value = ROLE_PAGE_MIN_SIZE,
            message = "Page size must be greater than or equal to " + ROLE_PAGE_MIN_SIZE)
        @Max(value = ROLE_PAGE_MAX_SIZE,
            message = "Page size must be less than or equal to " + ROLE_PAGE_MAX_SIZE) int size) {

        Page<Role> rolesPage = roleService.getAllRoles(page, size);
        return ResponseEntity.ok().body(rolesPage);
    }

    @ApiOperation(value = "Find a role by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return the role with corresponding ID"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Role not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/{roleId}", produces = "application/json")
    public ResponseEntity<Role> findRoleById(

        @PathVariable @Positive(message = "Role ID must be a positive integer") Integer roleId) {

        Role role = roleService.getRoleById(roleId);
        return ResponseEntity.ok().body(role);
    }

    @ApiOperation(value = "Find all roles for a user ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return the role with corresponding ID"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Role not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<List<Role>> findRolesByUserId(

        @PathVariable @Positive(message = "User ID must be a positive integer") Long userId) {

        List<Role> userRoles = roleService.getRolesByUserId(userId);
        return ResponseEntity.ok().body(userRoles);
    }

    @ApiOperation(value = "Add a new role")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "New role created"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Role> createRole(

        @Valid @RequestBody Role role) {

        Role newRole = roleService.createRole(role);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles/{roleId}").
                buildAndExpand(newRole.getRoleId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Delete an existing role")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Role deleted"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @DeleteMapping(value = "/{roleId}")
    public ResponseEntity<Void> deleteRole(

        @PathVariable @Positive(message = "Role ID must be a positive integer") Integer roleId) {

        roleService.deleteRoles(roleId);
        return ResponseEntity.noContent().build();
    }
}
