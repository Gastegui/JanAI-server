package com.pbl.demo.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }

    @Test
    void testSendSimpleMessage_Success() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "This is a test message.";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        // Act
        emailService.sendSimpleMessage(to, subject, text);

        // Assert
        verify(mailSender, times(1)).send(Mockito.refEq(message));
    }


    @Test
    void testSendHtmlMessage_Success() throws MessagingException {
        // Arrange
        String to = "test@example.com";
        String subject = "Test HTML Subject";
        String htmlBody = "<h1>This is a test</h1>";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailService.sendHtmlMessage(to, subject, htmlBody);

        // Assert
        verify(mailSender, times(1)).send(mimeMessage);
    }


}
