package com.dev.timetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * We use this class to transfer the data between the controller layer and the view layer.
 * And also for form binding.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
