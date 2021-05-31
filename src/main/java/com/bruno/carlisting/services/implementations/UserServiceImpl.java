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
        return user.orElseThrow(() -> new ObjectNotFoundException(String.format("User ID %s not found", userId)));
    }

    @Override
    public User getUserByCarId(Long carId) {

        Optional<User> user = userRepository.findById(userRepository.searchUserByCarId(carId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Car ID %s not found", carId))));
        return user.orElseThrow(() -> new ObjectNotFoundException(String.format(
                "User ID %s did not create Car ID %s", user.get().getUserId(), carId)));
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
                    "Username %s already exists", newUser.getUsername()));
        }
    }

    @Override
    public User updateUser(User updatedUser, List<Integer> rolesIds, Long userId) {

        User currentUser = getUserById(userId);
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
                    "Username %s already exists", updatedUser.getUsername()));
        }
    }

    @Override
    public User updateUserPassword(String password, Long userId) {

        User currentUser = getUserById(userId);

        currentUser.setPassword(password);
        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserDisplayName(String displayName, Long userId) {

        User currentUser = getUserById(userId);

        currentUser.setDisplayName(displayName);
        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserContact(String contact, Long userId) {

        User currentUser = getUserById(userId);

        currentUser.setContact(contact);
        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Long userId) {

        User userToDelete = getUserById(userId);

        try {
            userRepository.delete(userToDelete);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    "User ID %s has cars associated therefore cannot be deleted", userId));
        }
    }
}
