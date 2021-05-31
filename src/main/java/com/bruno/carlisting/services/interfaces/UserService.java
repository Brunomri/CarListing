package com.bruno.carlisting.services.interfaces;

import com.bruno.carlisting.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<User> getAllUsers(int page, int size);

    User getUserById(Long userId);

    User getUserByCarId(Long carId);

    User createUser(User newUser, List<Integer> rolesIds);

    User updateUser(User updatedUser, List<Integer> rolesIds, Long userId);

    User updateUserPassword(String password, Long userId);

    User updateUserDisplayName(String displayName, Long userId);

    void deleteUser(Long userId);

}
