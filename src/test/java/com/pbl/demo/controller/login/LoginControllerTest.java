package com.pbl.demo.controller.login;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.security.AuthRequest;
import com.pbl.demo.security.AuthResponse;
import com.pbl.demo.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDataRepository userRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ValidCredentials_ReturnsAuthResponse() {
        AuthRequest authRequest = new AuthRequest("testuser", "password");
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = "mockToken";
        String role = "ROLE_USER";
        Date expiration = new Date();

        when(userDetailsService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);
        when(jwtUtil.extractExpiration(token)).thenReturn(expiration);

        ResponseEntity<Object> response = loginController.authenticate(authRequest);

        assertEquals(200, response.getStatusCodeValue());
        AuthResponse authResponse = (AuthResponse) response.getBody();
        assertNotNull(authResponse);
        assertEquals(token, authResponse.getToken());
        assertEquals(role, authResponse.getRole());
        assertEquals(expiration, authResponse.getTimeout());
    }

    @Test
    void authenticate_InvalidUsername_ReturnsUnauthorized() {
        AuthRequest authRequest = new AuthRequest("invaliduser", "password");

        when(userDetailsService.loadUserByUsername(authRequest.getUsername()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        ResponseEntity<Object> response = loginController.authenticate(authRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void getUserss_ReturnsUserList() {
        List<UserData> userList = new ArrayList<>();
        userList.add(new UserData());

        when(userRepository.findAll()).thenReturn(userList);

        ResponseEntity<List<UserData>> response = loginController.getUserss();

        assertEquals(202, response.getStatusCodeValue());
        assertEquals(userList, response.getBody());
    }

    @Test
    void addUser_ValidUser_ReturnsCreated() {
        UserData newUser = new UserData();
        newUser.setUsername("newuser");

        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        ResponseEntity<UserData> response = loginController.addUser(newUser);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(newUser, response.getBody());
    }

    @Test
    void addUser_ExistingUser_ReturnsBadRequest() {
        UserData existingUser = new UserData();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        ResponseEntity<UserData> response = loginController.addUser(existingUser);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void putUsers_ExistingUser_ReturnsOk() {
        UserData updatedUser = new UserData();
        updatedUser.setUsername("updateduser");
        updatedUser.setUname("Updated Name");
        UserData existingUser = new UserData();

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        ResponseEntity<UserData> response = loginController.putUsers(1, updatedUser);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedUser.getUsername(), existingUser.getUsername());
    }

    @Test
    void deleteUser_ExistingUser_ReturnsOk() {
        UserData existingUser = new UserData();

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).delete(existingUser);

        ResponseEntity<UserData> response = loginController.deleteUser(1);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteUser_NonExistingUser_ReturnsNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<UserData> response = loginController.deleteUser(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}

