package com.dev.timetracker.service;

import com.dev.timetracker.dto.UserDto;
import com.dev.timetracker.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
}
