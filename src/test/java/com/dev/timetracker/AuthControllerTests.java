package com.dev.timetracker;

import com.dev.timetracker.controller.AuthController;
import com.dev.timetracker.dto.UserDto;
import com.dev.timetracker.entity.User;
import com.dev.timetracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<Object> objectCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrationWithExistingUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("existing@example.com");
        userDto.setPassword("password");

        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userService.findUserByEmail(userDto.getEmail())).thenReturn(existingUser);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = authController.registration(userDto, bindingResult, model);

        assertEquals("register", result);
        // Capture all calls to model.addAttribute
        verify(model, times(2)).addAttribute(stringCaptor.capture(), objectCaptor.capture());

        // Check the captured attributes
        assertTrue(stringCaptor.getAllValues().contains("error"));
        assertTrue(objectCaptor.getAllValues().contains(true));

        // Verify that saveUser method is not called
        verify(userService, never()).saveUser(any(UserDto.class));
    }
}
