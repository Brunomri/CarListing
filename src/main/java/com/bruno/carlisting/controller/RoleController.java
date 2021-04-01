package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Page<Role>> findAllRoles(@RequestParam(value = "page", required = false,
                                                   defaultValue = "0") int page,
                                                   @RequestParam(value = "size", required = false,
                                                   defaultValue = "10") int size) {

        Page<Role> rolesPage = roleService.getAllRoles(page, size);

        if(rolesPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok().body(rolesPage);
        }

    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Role> findRoleByUserId(@PathVariable Long userId) {
        Role role = roleService.getRoleByUserId(userId);

        if(role == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok().body(role);
        }

    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        Role newRole = roleService.createRole(role);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles/{roleId}").
                buildAndExpand(newRole.getRoleId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
