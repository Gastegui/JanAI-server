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

import com.pbl.demo.model.user_data.*;
import com.pbl.demo.model.weight_goals.*;

class WeightControllerTest {

    @InjectMocks
    private WeightController weightController;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private WeightGoalsRepository weightRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddWeightGoal_UserNotFound() {
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.empty());

        WeightGoals goal = new WeightGoals();
        ResponseEntity<WeightGoals> response = weightController.addWeightGoal("username", goal, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddWeightGoal_UserFound() {
        UserData user = new UserData();
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.of(user));

        WeightGoals goal = new WeightGoals();
        ResponseEntity<WeightGoals> response = weightController.addWeightGoal("username", goal, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(goal, response.getBody());
        verify(weightRepo, times(1)).save(goal);
    }

    @Test
    void testGetWeightList_UserNotFound() {
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.empty());

        ResponseEntity<List<WeightGoals>> response = weightController.getWeightList("username");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetWeightList_UserFound() {
        UserData user = new UserData();
        when(userRepo.findByUsername("username"))
            .thenReturn(Optional.of(user));

        WeightGoals goal = new WeightGoals();
        List<WeightGoals> goals = List.of(goal);
        when(weightRepo.findAll()).thenReturn(goals);

        ResponseEntity<List<WeightGoals>> response = weightController.getWeightList("username");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(goals, response.getBody());
    }
}
