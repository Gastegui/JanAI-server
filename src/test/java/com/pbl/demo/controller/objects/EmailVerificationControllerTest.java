package com.pbl.demo.controller.objects;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

class EmailVerificationControllerTest {

    @InjectMocks
    private EmailVerificationController emailVerificationController;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private BindingResult bindingResult;

    private UserData user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserData();
    }

    @Test
    void testGetVerificationCode_ValidRequest_Success() {
        // Arrange
        String writtenCode = "12345";
        String verifyMail = "12345";

        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        ResponseEntity<UserData> response = emailVerificationController.getVerificationCode(writtenCode, verifyMail, user, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepo, times(1)).save(user);
        assertEquals(0f, user.getFinalDailyCalorieIntake());
        assertEquals(0, user.getWaterCounter());
        assertEquals(0f, user.getWaterIntake());
    }

    @Test
    void testGetVerificationCode_InvalidCode_BadRequest() {
        // Arrange
        String writtenCode = "12345";
        String verifyMail = "54321";

        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        ResponseEntity<UserData> response = emailVerificationController.getVerificationCode(writtenCode, verifyMail, user, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userRepo, never()).save(any(UserData.class));
    }

    @Test
    void testGetVerificationCode_BindingErrors_BadRequest() {
        // Arrange
        String writtenCode = "12345";
        String verifyMail = "12345";

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("field", "Validation error")));

        // Act
        ResponseEntity<UserData> response = emailVerificationController.getVerificationCode(writtenCode, verifyMail, user, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Validation-Error"));
        verify(userRepo, never()).save(any(UserData.class));
    }

    @Test
    void testGetVerificationCode_BindingErrors_NoErrors() {
        // Arrange
        String writtenCode = "12345";
        String verifyMail = "12345";

        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        ResponseEntity<UserData> response = emailVerificationController.getVerificationCode(writtenCode, verifyMail, user, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepo, times(1)).save(user);
    }
}
