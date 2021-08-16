package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.exceptions.entityRelationshipIntegrityException;
import com.bruno.carlisting.repositories.UserRepository;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.RoleService;
import com.bruno.carlisting.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String USER_ID_NOT_FOUND = "User ID %s not found";
    public static final String PAGE_HAS_NO_USERS = "Page %s has no users";
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

        var pageRequest = PageRequest.of(page, size);
        var usersPage = userRepository.findAll(pageRequest);

        log.debug("method = getAllUsers, page number = {}, page size = {}, usersPage = {}",
                usersPage.getPageable().getPageNumber(), usersPage.getPageable().getPageSize(), usersPage.getContent());

        pagingService.validatePage(usersPage, String.format(PAGE_HAS_NO_USERS, page));
        return usersPage;
    }

    @Override
    public User getUserById(Long userId) {

        var user = userRepository.findById(userId);

        log.debug("method = getUserById, user = {}", user);

        return user.orElseThrow(() -> new ObjectNotFoundException(String.format(USER_ID_NOT_FOUND, userId)));
    }

    @Override
    public User getUserByCarId(Long carId) {

        var user = userRepository.findById(userRepository.searchUserByCarId(carId).orElseThrow(
                () -> new ObjectNotFoundException(String.format(CAR_ID_NOT_FOUND, carId))));

        log.debug("method = getUserByCarId, carId = {}, user = {}", carId, user);

        return user.orElseThrow(() -> new ObjectNotFoundException(String.format(
                USER_DID_NOT_CREATE_CAR, user.get().getUserId(), carId)));
    }

    @Override
    public User createUser(User newUser, List<Integer> rolesIds) {

        List<Role> userRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> userRoles.add(roleService.getRoleById(roleId)));
        newUser.setRoles(userRoles);

        try {

            log.debug("method = createUser, newUser = {}, rolesIds = {}", newUser, rolesIds);

            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {

            log.warn("Entity relationship integrity exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    USER_ALREADY_EXISTS, newUser.getUsername()));
        }
    }

    @Override
    public User updateUser(User updatedUser, List<Integer> rolesIds, Long userId) {

        var currentUser = getUserById(userId);

        log.debug("method = updateUser, currentUser = {}", currentUser);

        List<Role> updatedUserRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> updatedUserRoles.add(roleService.getRoleById(roleId)));
        updatedUser.setRoles(updatedUserRoles);

        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setPassword(updatedUser.getPassword());
        currentUser.setDisplayName(updatedUser.getDisplayName());
        currentUser.setContact(updatedUser.getContact());
        currentUser.setRoles(updatedUser.getRoles());

        log.debug("method = updateUser, currentUser = {}", currentUser);

        try {
            return userRepository.save(currentUser);
        } catch (DataIntegrityViolationException e) {

            log.warn("Data integrity violation exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    USER_ALREADY_EXISTS, updatedUser.getUsername()));
        }
    }

    @Override
    public User updateUserPassword(String password, Long userId) {

        var currentUser = getUserById(userId);

        log.debug("method = updateUserPassword, currentUser = {}", currentUser);

        currentUser.setPassword(password);

        log.debug("method = updateUserPassword, currentUser = {}", currentUser);

        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserDisplayName(String displayName, Long userId) {

        var currentUser = getUserById(userId);

        log.debug("method = updateUserDisplayName, currentUser = {}", currentUser);

        currentUser.setDisplayName(displayName);

        log.debug("method = updateUserDisplayName, currentUser = {}", currentUser);

        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserContact(String contact, Long userId) {

        var currentUser = getUserById(userId);

        log.debug("method = updateUserContact, currentUser = {}", currentUser);

        currentUser.setContact(contact);

        log.debug("method = updateUserContact, currentUser = {}", currentUser);

        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserRoles(List<Integer> rolesIds, Long userId) {

        var currentUser = getUserById(userId);

        log.debug("method = updateUserRoles, currentUser = {}", currentUser);

        List<Role> updatedUserRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> updatedUserRoles.add(roleService.getRoleById(roleId)));
        currentUser.setRoles(updatedUserRoles);

        log.debug("method = updateUserRoles, currentUser = {}", currentUser);

        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Long userId) {

        var userToDelete = getUserById(userId);

        try {

            log.debug("method = deleteUser, userToDelete: {}", userToDelete);

            userRepository.delete(userToDelete);
        } catch (DataIntegrityViolationException e) {

            log.warn("Entity relationship integrity exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    USER_IS_ASSOCIATED_TO_CARS, userId));
        }
    }
}
