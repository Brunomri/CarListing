package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

//    todo: mock User to set id

    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private RoleService mockedRoleService;

    @Test
    public void retrieveAllExistingUsers() {
        List<User> newUsers = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        newUsers.add(user1);
        newUsers.add(user2);
        newUsers.add(user3);

        Pageable pageRequest = PageRequest.of(0, 3);
        Page<User> usersPage = new PageImpl<>(newUsers);
        when(mockedUserRepository.findAll(pageRequest)).thenReturn(usersPage);

        UserService userService = new UserServiceImpl(mockedUserRepository, mockedRoleService);
        assertEquals(3, userService.getAllUsers(0, 3).getTotalElements());

    }

    @Test
    public void findExistingUserById() {
        Optional<User> user = Optional.of(new User());
//        user.get().setUserId(123456L);
        when(mockedUserRepository.findById(123456L)).thenReturn(user);

        UserService userService = new UserServiceImpl(mockedUserRepository, mockedRoleService);
        assertEquals(user.get(), userService.getUserById(123456L));

    }

    @Test
    public void findExistingUserByCarId() {
        Optional<User> user = Optional.of(new User());
//        user.get().setUserId(123456L);

        when(mockedUserRepository.searchUserByCarId(987654L)).thenReturn(123456L);
        when(mockedUserRepository.findById(123456L)).thenReturn(user);

        UserService userService = new UserServiceImpl(mockedUserRepository, mockedRoleService);
        assertEquals(user.get(), userService.getUserByCarId(987654L));

    }

    @Test
    public void createUser() {
        User user = new User();
//        user.setUserId(1L);

        List<Integer> userRoles = new ArrayList<>();

        Optional<Role> role1 = Optional.of(new Role());
//        role1.get().setRoleId(1);

        Optional<Role> role2 = Optional.of(new Role());
//        role2.get().setRoleId(2);

        userRoles.add(role1.get().getRoleId());
        userRoles.add(role2.get().getRoleId());

        when(mockedRoleService.getRoleById(1)).thenReturn(role1.get());
        when(mockedRoleService.getRoleById(2)).thenReturn(role2.get());
        when(mockedUserRepository.save(user)).thenReturn(user);

        UserService userService = new UserServiceImpl(mockedUserRepository, mockedRoleService);
        User newUser = userService.createUser(user, userRoles);
        assertNotNull(newUser);
        assertEquals(role1.get().getRoleId(), newUser.getRoles().get(0).getRoleId());
        assertEquals(role2.get().getRoleId(), newUser.getRoles().get(1).getRoleId());

    }

}
