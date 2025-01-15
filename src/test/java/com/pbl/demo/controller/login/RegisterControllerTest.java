package com.pbl.demo.controller.login;


import com.pbl.demo.mail.EmailVerificationService;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_NullBody_ReturnsBadRequest() {
        // Act
        ResponseEntity<EmailVerificationService> response = registerController.registerUser(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void registerUser_ExistingUser_ReturnsBadRequest() {
        // Arrange
        UserData existingUser = new UserData();
        existingUser.setUsername("existinguser");

        when(userRepo.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<EmailVerificationService> response = registerController.registerUser(existingUser);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void registerUser_NewUser_ReturnsOk() {
        // Arrange
        UserData newUser = new UserData();
        newUser.setUsername("newuser");
        newUser.setUserPass("plaintextpassword");

        when(userRepo.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newUser.getUserPass())).thenReturn("encryptedpassword");
        doNothing().when(emailVerificationService).sendVerificationCode(newUser);

        // Act
        ResponseEntity<EmailVerificationService> response = registerController.registerUser(newUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(passwordEncoder, times(1)).encode("plaintextpassword");
        verify(emailVerificationService, times(1)).sendVerificationCode(newUser);
    }
}

