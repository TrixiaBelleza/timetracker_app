package com.dev.timetracker.service.impl;

import com.dev.timetracker.dto.UserDto;
import com.dev.timetracker.entity.User;
import com.dev.timetracker.repository.UserRepository;
import com.dev.timetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public void saveUser(UserDto userDto) {
        try {
            User user = new User();
            String name = userDto.getFirstName() + " " + userDto.getLastName();
            user.setName(name);
            user.setEmail(user.getEmail());

            // Encrypt password using PasswordEncoder
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            logger.debug("Successfully saved user: " + user.getId());
        } catch (Exception e) {
            logger.warn("Failed to save user with user id: " + userDto.getId() + " due to: " + e.getMessage(), e);
        }
    }
}
