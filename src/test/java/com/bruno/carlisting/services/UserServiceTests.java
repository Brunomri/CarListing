package com.bruno.carlisting.services;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

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
        user.get().setUserId(123456L);
        when(mockedUserRepository.findById(123456L)).thenReturn(user);

        UserService userService = new UserServiceImpl(mockedUserRepository, mockedRoleService);
        assertEquals(user.get(), userService.getUserById(123456L));

    }

    @Test
    public void findExistingUserByCarId() {
        Optional<User> user = Optional.of(new User());
        user.get().setUserId(123456L);

        when(mockedUserRepository.searchUserByCarId(987654L)).thenReturn(123456L);
        when(mockedUserRepository.findById(123456L)).thenReturn(user);

        UserService userService = new UserServiceImpl(mockedUserRepository, mockedRoleService);
        assertEquals(user.get(), userService.getUserByCarId(987654L));

    }

}
