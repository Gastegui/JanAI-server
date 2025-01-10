package com.pbl.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ControllerUtilsTest {

    @Test
    void testSetSessionMessagesWithErrorAndInfo() {
        // Arrange
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        when(session.getAttribute("error")).thenReturn("Error message");
        when(session.getAttribute("info")).thenReturn("Info message");

        // Act
        ControllerUtils.setSessionMessages(session, model);

        // Assert
        verify(model).addAttribute("errorMsg", "Error message");
        verify(session).removeAttribute("error");
        verify(model).addAttribute("infoMsg", "Info message");
        verify(session).removeAttribute("info");
    }

    @Test
    void testSetSessionMessagesWithoutMessages() {
        // Arrange
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        when(session.getAttribute("error")).thenReturn(null);
        when(session.getAttribute("info")).thenReturn(null);

        // Act
        ControllerUtils.setSessionMessages(session, model);

        // Assert
        verify(model, never()).addAttribute(anyString(), any());
        verify(session, never()).removeAttribute(anyString());
    }

    @Test
    void testIsPasswordValid_ValidPassword() {
        // Arrange
        HttpSession session = mock(HttpSession.class);
        String validPassword = "Abc12345";

        // Act
        boolean result = ControllerUtils.isPasswordValid(validPassword, session);

        // Assert
        assertTrue(result);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    void testIsPasswordValid_InvalidLength() {
        // Arrange
        HttpSession session = mock(HttpSession.class);
        String invalidPassword = "Abc12";

        // Act
        boolean result = ControllerUtils.isPasswordValid(invalidPassword, session);

        // Assert
        assertFalse(result);
        verify(session).setAttribute("error", "La contraseña debe contener al menos 8 caracteres.");
    }

    @Test
    void testIsPasswordValid_NoLettersOrNumbers() {
        // Arrange
        HttpSession session = mock(HttpSession.class);
        String invalidPassword = "12345678";

        // Act
        boolean result = ControllerUtils.isPasswordValid(invalidPassword, session);

        // Assert
        assertFalse(result);
        verify(session).setAttribute("error", "La contraseña debe contener letras y numeros");
    }

    @Test
    void testGetLoggedInUsername() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        // Act
        String username = ControllerUtils.getLoggedInUsername();

        // Assert
        assertEquals("testUser", username);
    }

    /*@Test
    void testGetLoggedInRole() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        GrantedAuthority authority = mock(GrantedAuthority.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn((Collection<? extends GrantedAuthority>) Collections.singletonList(authority));

        when(authority.getAuthority()).thenReturn("ROLE_USER");

        // Act
        String role = ControllerUtils.getLoggedInRole();

        // Assert
        assertEquals("ROLE_USER", role);
    }*/

    /*@Test
    void testHasAuthority() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        GrantedAuthority authority = mock(GrantedAuthority.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getAuthorities()).thenReturn(Collections.singletonList(authority));
        when(authority.getAuthority()).thenReturn("ROLE_ADMIN");

        // Act
        boolean result = ControllerUtils.hasAuthority("ROLE_ADMIN");

        // Assert
        assertTrue(result);
    }*/

    @Test
    void testGenerateRandomString() {
        // Act
        String randomString = ControllerUtils.generateRandomString(10);

        // Assert
        assertNotNull(randomString);
        assertEquals(10, randomString.length());
        assertTrue(randomString.matches("[A-Za-z0-9]+"));
    }

    @Test
    void testIterableToArrayList() {
        // Arrange
        List<String> list = List.of("one", "two", "three");

        // Act
        List<String> result = ControllerUtils.iterableToArrayList(list);

        // Assert
        assertEquals(list, result);
    }
}
