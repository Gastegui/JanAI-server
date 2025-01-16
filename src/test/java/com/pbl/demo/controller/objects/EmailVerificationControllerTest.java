package com.pbl.demo.controller.objects;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailVerificationControllerTest {

    @InjectMocks
    private EmailVerificationController emailVerificationController;

    @Mock
    private UserDataRepository userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVerificationCode_CorrectCode_ReturnsCreated() {
        // Arrange
        String writtenCode = "123456";
        String verifyMail = "123456";
        UserData user = new UserData();

        // Act
        ResponseEntity<UserData> response = emailVerificationController.getVerificationCode(writtenCode, verifyMail, user, null);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void getVerificationCode_IncorrectCode_ReturnsBadRequest() {
        // Arrange
        String writtenCode = "123456";
        String verifyMail = "654321";
        UserData user = new UserData();

        // Act
        ResponseEntity<UserData> response = emailVerificationController.getVerificationCode(writtenCode, verifyMail, user, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userRepo, never()).save(user);
    }
}
