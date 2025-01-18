package com.pbl.demo.mail;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pbl.demo.model.user_data.UserData;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class EmailVerificationServiceTest {

    private EmailService emailService;
    private EmailVerificationService emailVerificationService;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        emailVerificationService = new EmailVerificationService(emailService);
    }

    @Test
    void testSendVerificationCode_Success() throws MessagingException {
        // Arrange
        UserData user = new UserData();
        user.setEmail("test@example.com");

        // Act
        emailVerificationService.sendVerificationCode(user);

        // Assert
        verify(emailService, times(1)).sendHtmlMessage(eq("test@example.com"), eq("Email verification"), anyString());
        assertNotNull(emailVerificationService.getVerificationCode());
    }

    @Test
    void testSendVerificationCode_GeneratesValidCode() {
        UserData user = new UserData();
        
        // Act
        emailVerificationService.sendVerificationCode(user);
        Integer verificationCode = emailVerificationService.getVerificationCode();

        // Assert
        assertNotNull(verificationCode);
        assertTrue(verificationCode >= 100000 && verificationCode <= 999999, "Verification code is out of range.");
    }

    @Test
    void testSendVerificationCode_ValidHtmlBody() throws MessagingException {
        // Arrange
        UserData user = new UserData();
        user.setEmail("test@example.com");

        // Act
        emailVerificationService.sendVerificationCode(user);

        // Capture the HTML body passed to `sendHtmlMessage`
        ArgumentCaptor<String> htmlBodyCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendHtmlMessage(eq("test@example.com"), eq("Email verification"), htmlBodyCaptor.capture());

        // Assert
        String htmlBody = htmlBodyCaptor.getValue();
        Integer verificationCode = emailVerificationService.getVerificationCode();
        assertNotNull(htmlBody);
        assertTrue(htmlBody.contains(verificationCode.toString()), "HTML body does not contain the verification code.");
    }

    @Test
    void testSendVerificationCode_HandleMessagingException() throws MessagingException {
        // Arrange
        UserData user = new UserData();
        user.setEmail("test@example.com");
        doThrow(MessagingException.class).when(emailService).sendHtmlMessage(anyString(), anyString(), anyString());

        // Act
        assertDoesNotThrow(() -> emailVerificationService.sendVerificationCode(user));

        // Assert
        verify(emailService, times(1)).sendHtmlMessage(anyString(), anyString(), anyString());
    }

    @Test
    void testGenerateVerificationCode_Randomness() {
        UserData user1 = new UserData();
        UserData user2 = new UserData();


        // Act
        emailVerificationService.sendVerificationCode(user1);
        Integer code1 = emailVerificationService.getVerificationCode();

        emailVerificationService.sendVerificationCode(user2);
        Integer code2 = emailVerificationService.getVerificationCode();

        // Assert
        assertNotEquals(code1, code2, "Generated codes should not be identical.");
    }


}
