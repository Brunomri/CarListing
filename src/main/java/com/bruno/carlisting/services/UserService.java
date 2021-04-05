package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<User> getAllUsers(int page, int size);

    User getUserById(Long userId);

    User getUserByCarId(Long carId);

    User createUser(User newUser, List<Integer> rolesIds);

    User updateUser(User user, Long userId, int page, int size);

    void deleteUser(Long userId);

}
