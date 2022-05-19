package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Role;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.repositories.UserRepository;
import com.bruno.carlisting.services.implementations.UserServiceImpl;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

//    todo: mock User to set id

    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private RoleService mockedRoleService;

    @Mock
    private PagingService mockedPagingService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void retrieveAllExistingUsers() {
        List<User> newUsers = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        newUsers.add(user1);
        newUsers.add(user2);
        newUsers.add(user3);

        Pageable pageRequest = PageRequest.of(0, 3);
        Page<User> usersPage = new PageImpl<>(newUsers, pageRequest, newUsers.size());
        when(mockedUserRepository.findAll(pageRequest)).thenReturn(usersPage);

        assertEquals(3, userService.getAllUsers(0, 3).getTotalElements());
        verify(mockedUserRepository, times(1)).findAll(any(Pageable.class));
        verify(mockedPagingService, times(1)).validatePage(any(Page.class), anyString());
    }

    @Test
    void findExistingUserById() {
        Optional<User> user = Optional.of(new User());
//        user.get().setUserId(123456L);
        when(mockedUserRepository.findById(123456L)).thenReturn(user);

        assertEquals(user.get(), userService.getUserById(123456L));
        verify(mockedUserRepository, times(1)).findById(anyLong());
        verifyNoInteractions(mockedPagingService);
    }

    @Test
    void findExistingUserByCarId() {
        Optional<User> user = Optional.of(new User());
//        user.get().setUserId(123456L);

        when(mockedUserRepository.searchUserByCarId(987654L)).thenReturn(Optional.of(123456L));
        when(mockedUserRepository.findById(123456L)).thenReturn(user);

        assertEquals(user.get(), userService.getUserByCarId(987654L));
        verify(mockedUserRepository, times(1)).searchUserByCarId(anyLong());
        verify(mockedUserRepository, times(1)).findById(anyLong());
        verifyNoInteractions(mockedPagingService);
    }

    @Test
    void createUser() {
        User user = new User();
//        user.setUserId(1L);
//        ReflectionTestUtils.setField(user, "userId", 1L);

        List<Integer> userRoles = new ArrayList<>();

        Optional<Role> role1 = Optional.of(new Role());
//        role1.get().setRoleId(1);
        ReflectionTestUtils.setField(role1.get(), "roleId", 1);

        Optional<Role> role2 = Optional.of(new Role());
//        role2.get().setRoleId(2);
        ReflectionTestUtils.setField(role2.get(), "roleId", 2);

        userRoles.add(role1.get().getRoleId());
        userRoles.add(role2.get().getRoleId());

        when(mockedRoleService.getRoleById(1)).thenReturn(role1.get());
        when(mockedRoleService.getRoleById(2)).thenReturn(role2.get());
        when(mockedUserRepository.save(user)).thenReturn(user);

        User newUser = userService.createUser(user, userRoles);
        assertNotNull(newUser);
        assertEquals(role1.get().getRoleId(), newUser.getRoles().get(0).getRoleId());
        assertEquals(role2.get().getRoleId(), newUser.getRoles().get(1).getRoleId());
        verify(mockedRoleService, times(2)).getRoleById(anyInt());
        verify(mockedUserRepository, times(1)).save(any(User.class));
        verifyNoInteractions(mockedPagingService);
    }
}
