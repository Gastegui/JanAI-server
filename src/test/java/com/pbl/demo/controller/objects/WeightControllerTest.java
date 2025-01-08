package com.pbl.demo.controller.objects;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.model.weightGoals.WeightGoals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
class WeightControllerTest {

    @InjectMocks
    private WeightController weightController;

    @Mock
    private UserDataRepository userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addWeightGoal_UserExists_ReturnsCreated() {
        // Arrange
        String username = "testUser";
        WeightGoals goal = new WeightGoals();
        UserData user = new UserData();
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<WeightGoals> response = weightController.addWeightGoal(username, goal);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(goal, response.getBody());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void addWeightGoal_UserNotFound_ReturnsBadRequest() {
        // Arrange
        String username = "nonExistentUser";
        WeightGoals goal = new WeightGoals();
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<WeightGoals> response = weightController.addWeightGoal(username, goal);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userRepo, never()).save(any(UserData.class));
    }

    @Test
    void getWeightList_UserExists_ReturnsWeightGoals() {
        // Arrange
        String username = "testUser";
        UserData user = new UserData();
        WeightGoals goal1 = new WeightGoals();
        WeightGoals goal2 = new WeightGoals();
        user.setWeightGoals(List.of(goal1, goal2));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<List<WeightGoals>> response = weightController.getWeightList(username);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getWeightList_UserNotFound_ReturnsNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<WeightGoals>> response = weightController.getWeightList(username);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
