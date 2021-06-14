package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.exceptions.entityRelationshipIntegrityException;
import com.bruno.carlisting.repositories.UserRepository;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.RoleService;
import com.bruno.carlisting.services.interfaces.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final String USER_ID_NOT_FOUND = "User ID %s not found";
    public static final String CAR_ID_NOT_FOUND = "Car ID %s not found";
    public static final String USER_DID_NOT_CREATE_CAR = "User ID %s did not create Car ID %s";
    public static final String USER_ALREADY_EXISTS = "Username %s already exists";
    public static final String USER_IS_ASSOCIATED_TO_CARS = "User ID %s has cars associated therefore cannot be deleted";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PagingService pagingService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PagingService pagingService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.pagingService = pagingService;
    }

    @Override
    public Page<User> getAllUsers(int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageRequest);
        pagingService.validatePage(usersPage);
        return usersPage;
    }

    @Override
    public User getUserById(Long userId) {

        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new ObjectNotFoundException(String.format(USER_ID_NOT_FOUND, userId)));
    }

    @Override
    public User getUserByCarId(Long carId) {

        Optional<User> user = userRepository.findById(userRepository.searchUserByCarId(carId).orElseThrow(
                () -> new ObjectNotFoundException(String.format(CAR_ID_NOT_FOUND, carId))));
        return user.orElseThrow(() -> new ObjectNotFoundException(String.format(
                USER_DID_NOT_CREATE_CAR, user.get().getUserId(), carId)));
    }

//    todo: Create User DTO to avoid passing rolesIds parameter
    @Override
    public User createUser(User newUser, List<Integer> rolesIds) {

        List<Role> userRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> userRoles.add(roleService.getRoleById(roleId)));
        newUser.setRoles(userRoles);

        try {
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    USER_ALREADY_EXISTS, newUser.getUsername()));
        }
    }

    @Override
    public User updateUser(User updatedUser, List<Integer> rolesIds, Long userId) {

        var currentUser = getUserById(userId);
        List<Role> updatedUserRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> updatedUserRoles.add(roleService.getRoleById(roleId)));
        updatedUser.setRoles(updatedUserRoles);

        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setPassword(updatedUser.getPassword());
        currentUser.setDisplayName(updatedUser.getDisplayName());
        currentUser.setContact(updatedUser.getContact());
        currentUser.setRoles(updatedUser.getRoles());

        try {
            return userRepository.save(currentUser);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    USER_ALREADY_EXISTS, updatedUser.getUsername()));
        }
    }

    @Override
    public User updateUserPassword(String password, Long userId) {

        var currentUser = getUserById(userId);

        currentUser.setPassword(password);
        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserDisplayName(String displayName, Long userId) {

        var currentUser = getUserById(userId);

        currentUser.setDisplayName(displayName);
        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserContact(String contact, Long userId) {

        var currentUser = getUserById(userId);

        currentUser.setContact(contact);
        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserRoles(List<Integer> rolesIds, Long userId) {

        var currentUser = getUserById(userId);
        List<Role> updatedUserRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> updatedUserRoles.add(roleService.getRoleById(roleId)));
        currentUser.setRoles(updatedUserRoles);
        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Long userId) {

        var userToDelete = getUserById(userId);

        try {
            userRepository.delete(userToDelete);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    USER_IS_ASSOCIATED_TO_CARS, userId));
        }
    }
}
