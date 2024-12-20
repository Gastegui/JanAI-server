package com.pbl.demo.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private AdministratorRepository adminRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        UserData mockUser = new UserData();
        mockUser.setUsername("testUser");
        mockUser.setUserPass("password");

        when(userRepo.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(adminRepo.findByUsername("testUser")).thenReturn(Optional.empty());

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("USER")));
    }

    @Test
    void loadUserByUsername_AdminExists_ReturnsAdminDetails() {
        // Arrange
        Administrator mockAdmin = new Administrator();
        mockAdmin.setUsername("adminUser");
        mockAdmin.setUserPass("adminPass");

        when(userRepo.findByUsername("adminUser")).thenReturn(Optional.empty());
        when(adminRepo.findByUsername("adminUser")).thenReturn(Optional.of(mockAdmin));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("adminUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("adminUser", userDetails.getUsername());
        assertEquals("adminPass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN")));
    }

    @Test
    void loadUserByUsername_UserAndAdminNotFound_ThrowsException() {
        // Arrange
        when(userRepo.findByUsername("unknownUser")).thenReturn(Optional.empty());
        when(adminRepo.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, 
            () -> userDetailsService.loadUserByUsername("unknownUser"));
    }
}
