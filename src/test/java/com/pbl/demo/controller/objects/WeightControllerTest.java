package com.pbl.demo.controller.objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;
import com.pbl.demo.model.weight_goals.WeightGoals;
import com.pbl.demo.model.weight_goals.WeightGoalsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class WeightControllerTest {

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private WeightGoalsRepository weightRepo;

    @InjectMocks
    private WeightController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddWeightGoal_InvalidRequestBody() {
        // Arrange
        String username = "user1";
        WeightGoals goal = new WeightGoals();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        // Act
        ResponseEntity<WeightGoals> response = controller.addWeightGoal(username, goal, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Validation-Error"));
    }

    @Test
    void testAddWeightGoal_UserNotFound() {
        // Arrange
        String username = "user1";
        WeightGoals goal = new WeightGoals();
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<WeightGoals> response = controller.addWeightGoal(username, goal, mock(BindingResult.class));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddWeightGoal_Success() {
        // Arrange
        String username = "user1";
        WeightGoals goal = new WeightGoals();
        UserData user = new UserData();
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(weightRepo.save(goal)).thenReturn(goal);

        // Act
        ResponseEntity<WeightGoals> response = controller.addWeightGoal(username, goal, mock(BindingResult.class));

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(goal, response.getBody());
    }

    @Test
    void testGetWeightList_UserNotFound() {
        // Arrange
        String username = "user1";
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<WeightGoals>> response = controller.getWeightList(username);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetWeightList_EmptyList() {
        // Arrange
        String username = "user1";
        UserData user = new UserData();
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(weightRepo.findByUserDataUsername(username)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<WeightGoals>> response = controller.getWeightList(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetWeightList_Success() {
        // Arrange
        String username = "user1";
        UserData user = new UserData();
        List<WeightGoals> goals = List.of(new WeightGoals());
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(weightRepo.findByUserDataUsername(username)).thenReturn(goals);

        // Act
        ResponseEntity<List<WeightGoals>> response = controller.getWeightList(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(goals, response.getBody());
    }
}
