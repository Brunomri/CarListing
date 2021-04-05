package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.repositories.RoleRepository;
import com.bruno.carlisting.repositories.UserRepository;
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
//    private final CarService carService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService/*, CarService carService*/) {
        this.userRepository = userRepository;
        this.roleService = roleService;
//        this.carService = carService;
    }

    @Override
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + userId));
    }

    @Override
    public User getUserByCarId(Long carId) {

        Optional<User> user = userRepository.findById(userRepository.searchUserByCarId(carId));
        return user.orElseThrow(() -> new ObjectNotFoundException("User did not create this car! userId: " +
                user.get().getUserId() + "carId: " + carId));
    }

    @Override
    public User createUser(User newUser, List<Integer> rolesIds) {

        List<Role> userRoles = new ArrayList<>();
        rolesIds.forEach(roleId -> userRoles.add(roleService.getRoleById(roleId)));
        newUser.setRoles(userRoles);
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User updatedUser, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + userId));
        User currentUser = optionalUser.get();

        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setPassword(updatedUser.getPassword());
        currentUser.setDisplayName(updatedUser.getDisplayName());
        currentUser.setContact(updatedUser.getContact());
        currentUser.setRoles(roleService.getRolesByUserId(userId));

        return userRepository.save(currentUser);
    }

    @Override
    public User updateUserDisplayName(User user, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + userId));
        User currentUser = optionalUser.get();

        currentUser.setDisplayName(user.getDisplayName());
        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Long userId) {

        userRepository.deleteById(userId);

    }
}
