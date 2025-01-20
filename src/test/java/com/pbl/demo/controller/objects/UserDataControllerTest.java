package com.pbl.demo.controller.objects;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.pbl.demo.model.administrator.*;
import com.pbl.demo.model.user_data.*;

class UserDataControllerTest {

    @InjectMocks
    private UserDataController userDataController;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private AdministratorRepository adminRepo;

    @Mock
    private BindingResult result;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAdminByName_NotFound() {
        when(adminRepo.findByUsername("admin"))
            .thenReturn(Optional.empty());

        ResponseEntity<Administrator> response = userDataController.getAdminByName("admin");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAdminByName_Found() {
        Administrator admin = new Administrator();
        when(adminRepo.findByUsername("admin"))
            .thenReturn(Optional.of(admin));

        ResponseEntity<Administrator> response = userDataController.getAdminByName("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    void testGetUsers_EmptyList() {
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserData>> response = userDataController.getUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsers_NonEmptyList() {
        UserData user = new UserData();
        List<UserData> users = List.of(user);
        when(userRepo.findAll()).thenReturn(users);

        ResponseEntity<List<UserData>> response = userDataController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetUserDataById_NotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<UserData> response = userDataController.getUserDataById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserDataById_Found() {
        UserData user = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<UserData> response = userDataController.getUserDataById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUsersByName_NotFound() {
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.empty());

        ResponseEntity<UserData> response = userDataController.getUsersByName("username");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByName_Found() {
        //Arrange
        UserData user = new UserData();
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.of(user));

        //Act
        ResponseEntity<UserData> response = userDataController.getUsersByName("username");

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetFinalDailyCalorieIntakeByUsername_NotFound() {
        when(userRepo.findFinalDailyCalorieIntakeByUsername("username"))
            .thenReturn(Optional.empty());

        ResponseEntity<Float> response = userDataController.getFinalDailyCalorieIntakeByUsername("username");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetFinalDailyCalorieIntakeByUsername_Found() {
        when(userRepo.findFinalDailyCalorieIntakeByUsername("username"))
            .thenReturn(Optional.of(2000.0f));

        ResponseEntity<Float> response = userDataController.getFinalDailyCalorieIntakeByUsername("username");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2000.0f, response.getBody());
    }

    @Test
    void testAddUser_DuplicateUser() {
        UserData user = new UserData();
        user.setUsername("username");
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.of(user));

        ResponseEntity<UserData> response = userDataController.addUser(user, result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAddUser_Valid() {
        UserData user = new UserData();
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.empty());

        ResponseEntity<UserData> response = userDataController.addUser(user, result);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testPutUsers_NotFound() {
        UserData user = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<UserData> response = userDataController.putUsers(1, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPutUsers_Found() {
        UserData existingUser = new UserData();
        UserData updatedUser = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.of(existingUser));

        ResponseEntity<UserData> response = userDataController.putUsers(1, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepo, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<UserData> response = userDataController.deleteUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser_Found() {
        UserData user = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<UserData> response = userDataController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void testGetWaterCounterByUsername_UserNotFound_ReturnsNotFound() {
        // Mock data
        String username = "testUser";

        // Mocking repo calls
        when(userRepo.findWaterCounterByUsername(username)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<Integer> response = userDataController.getWaterCounterByUsername(username);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetWaterCounterByUsername_UserFound_ReturnsWaterCounter() {
        // Mock data
        String username = "testUser";
        int waterCounter = 5;

        // Mocking repo calls
        when(userRepo.findWaterCounterByUsername(username)).thenReturn(Optional.of(waterCounter));

        // Execute
        ResponseEntity<Integer> response = userDataController.getWaterCounterByUsername(username);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(waterCounter, response.getBody());
    }

    @Test
    void testGetWaterIntakeByUserID_UserNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;

        // Mocking repo calls
        when(userRepo.findWaterIntakeByUserID(userID)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<Float> response = userDataController.getWaterIntakeByUserID(userID);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetWaterIntakeByUserID_UserFound_ReturnsWaterIntake() {
        // Mock data
        int userID = 1;
        float waterIntake = 2.5f;

        // Mocking repo calls
        when(userRepo.findWaterIntakeByUserID(userID)).thenReturn(Optional.of(waterIntake));

        // Execute
        ResponseEntity<Float> response = userDataController.getWaterIntakeByUserID(userID);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(waterIntake, response.getBody());
    }

    @Test
    void testAddUser_BindingErrors_ReturnsBadRequest() {
        // Mock data
        UserData user = new UserData();
        user.setUserID(1);
        user.setUsername("testUser");
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(
                List.of(new ObjectError("user", "Invalid username"))
        );

        // Execute
        ResponseEntity<UserData> response = userDataController.addUser(user, result);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        assertTrue(response.getHeaders().containsKey("Validation-Error"));
        assertTrue(response.getHeaders().get("Validation-Error").get(0).contains("Invalid username"));
    }

    @Test
    void testAddUser_UserAlreadyExists_ReturnsBadRequest() {
        // Mock data
        UserData user = new UserData();
        user.setUserID(1);
        user.setUsername("testUser");
        // Mocking repo calls
        when(result.hasErrors()).thenReturn(false);
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Execute
        ResponseEntity<UserData> response = userDataController.addUser(user, result);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddUser_ValidUser_ReturnsCreated() {
        // Mock data
        UserData user = new UserData();
        user.setUserID(1);
        user.setUsername("testUser");
        // Mocking repo calls
        when(result.hasErrors()).thenReturn(false);
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.save(user)).thenReturn(user);

        // Execute
        ResponseEntity<UserData> response = userDataController.addUser(user, result);

        // Verify
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user, response.getBody());
    }
}
