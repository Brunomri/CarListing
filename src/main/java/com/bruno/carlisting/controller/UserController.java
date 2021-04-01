package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.services.UserService;
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
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAllUsers(@RequestParam(value = "page",
                                                   required = false,
                                                   defaultValue = "0") int page,
                                                   @RequestParam(value = "size",
                                                   required = false,
                                                   defaultValue = "10") int size) {

        Page<User> usersPage = userService.getAllUsers(page, size);

        if(usersPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok().body(usersPage);
        }

    }

    @GetMapping(value = "/cars/{carId}")
    public ResponseEntity<User> findUserByCarId(@PathVariable Long carId) {

        User user = userService.getUserByCarId(carId);
        return ResponseEntity.ok().body(user);

    }

    @PostMapping(value = "/{rolesIds}")
    public ResponseEntity<User> createUser(@PathVariable List<Integer> rolesIds, @Valid @RequestBody User user) {

        User newUser = userService.createUser(user, rolesIds);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{userId}").
                buildAndExpand(newUser.getUserId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
