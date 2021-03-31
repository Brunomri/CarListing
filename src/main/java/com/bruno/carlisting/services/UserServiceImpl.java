package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(int page, int size) {
        return null;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + userId));
    }
}
