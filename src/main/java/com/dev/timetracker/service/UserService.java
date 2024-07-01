package com.dev.timetracker.service;

import com.dev.timetracker.dto.UserDto;
import com.dev.timetracker.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    void saveUser(UserDto userDto);
}
