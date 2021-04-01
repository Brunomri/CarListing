package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> findCarsByMake(@PathVariable Long userId) {

        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);

    }
}
