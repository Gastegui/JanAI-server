package com.pbl.demo.controller.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;
import com.pbl.demo.security.AuthRequest;
import com.pbl.demo.security.AuthResponse;
import com.pbl.demo.security.JwtUtil;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticate_Success() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        UserDetails userDetails = User.withUsername("username").password("password").roles("USER").build();

        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("mockToken");
        when(jwtUtil.extractExpiration("mockToken")).thenReturn(new Date());

        ResponseEntity<Object> response = loginController.authenticate(authRequest);

        assertEquals(HttpStatus.OK , response.getStatusCode());
        AuthResponse authResponse = (AuthResponse) response.getBody();
        assertNotNull(authResponse);
        assertEquals("mockToken", authResponse.getToken());
        assertEquals("ROLE_USER", authResponse.getRole());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        AuthRequest authRequest = new AuthRequest("username", "wrongPassword");
        UserDetails userDetails = User.withUsername("username").password("password").roles("USER").build();

        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);

        ResponseEntity<Object> response = loginController.authenticate(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testAuthenticate_UserNotFound() {
        AuthRequest authRequest = new AuthRequest("username", "password");

        when(userDetailsService.loadUserByUsername("username")).thenThrow(new UsernameNotFoundException("User not found"));

        ResponseEntity<Object> response = loginController.authenticate(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testGetUsers_NoUsersFound() {
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserData>> response = loginController.getUsers();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetUsers_UsersFound() {
        UserData user = new UserData();
        user.setUsername("username");
        when(userRepo.findAll()).thenReturn(Collections.singletonList(user));

        ResponseEntity<List<UserData>> response = loginController.getUsers();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("username", response.getBody().get(0).getUsername());
    }

    @Test
    void testAddUser_Success() {
        UserData newUser = new UserData();
        newUser.setUsername("newUser");

        when(userRepo.findByUsername("newUser")).thenReturn(Optional.empty());
        when(userRepo.save(newUser)).thenReturn(newUser);

        ResponseEntity<UserData> response = loginController.addUser(newUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("newUser", response.getBody().getUsername());
    }

    @Test
    void testAddUser_Conflict() {
        UserData existingUser = new UserData();
        existingUser.setUsername("existingUser");

        when(userRepo.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));

        ResponseEntity<UserData> response = loginController.addUser(existingUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testPutUsers_UserNotFound() {
        UserData user = new UserData();

        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<UserData> response = loginController.putUsers(1, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPutUsers_Success() {
        UserData existingUser = new UserData();
        existingUser.setUsername("oldUsername");
        UserData updatedUser = new UserData();
        updatedUser.setUsername("newUsername");

        when(userRepo.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(existingUser)).thenReturn(updatedUser);

        ResponseEntity<UserData> response = loginController.putUsers(1, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("newUsername", response.getBody().getUsername());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<UserData> response = loginController.deleteUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser_Success() {
        UserData user = new UserData();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepo).delete(user);

        ResponseEntity<UserData> response = loginController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
