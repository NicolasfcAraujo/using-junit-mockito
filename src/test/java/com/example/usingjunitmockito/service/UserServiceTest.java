package com.example.usingjunitmockito.service;

import com.example.usingjunitmockito.domain.user.User;
import com.example.usingjunitmockito.dto.LoginDTO;
import com.example.usingjunitmockito.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should log in successfully")
    void loginCase1() throws Exception {
        String email = "name1@example.com";
        String password = "123";

        LoginDTO loginDTO = new LoginDTO(email, password);
        User mockUser =  new User("Name 1", email, password);

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        User loggedUser = userService.login(loginDTO);

        assertEquals(mockUser, loggedUser);
        verify(userRepository, times(1)).findByEmail(loginDTO.email());
    }

    @Test
    @DisplayName("Should throw an error to not equals passwords")
    void loginCase2() throws Exception {
        String email = "name2@example.com";

        LoginDTO loginDTO = new LoginDTO(email, "123");
        User mockUser =  new User("Name 1", email, "123456");

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        assertThrows(Exception.class, () -> {
            User loggedUser = userService.login(loginDTO);
        });
        verify(userRepository, times(1)).findByEmail(loginDTO.email());
    }

    @Test
    @DisplayName("Should throw an error to not found email")
    void loginCase3() throws Exception {
        String email = "name1@example.com";

        LoginDTO loginDTO = new LoginDTO(email, "123");

        Exception thrown = assertThrows(Exception.class, () -> {
            User loggedUser = userService.login(loginDTO);
        });

        assertEquals("Failed to find user with this email", thrown.getMessage());
        verify(userRepository, times(1)).findByEmail(loginDTO.email());
    }
}