package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;
import com.pbl.demo.model.weight_goals.WeightGoals;
import com.pbl.demo.model.weight_goals.WeightGoalsRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(WeightController.class)
@AutoConfigureMockMvc(addFilters = false)
class WeightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private WeightGoalsRepository weightRepo;

    @InjectMocks
    private WeightController weightController;

    public WeightControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testAddWeightGoal_Success() {
        String username = "testUser";
        WeightGoals goal = new WeightGoals();
        UserData user = new UserData();
        user.setUsername(username);

        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(weightRepo.save(any(WeightGoals.class))).thenReturn(goal);

        ResponseEntity<WeightGoals> response = weightController.addWeightGoal(username, goal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(goal, response.getBody());
        verify(userRepo).findByUsername(username);
        verify(weightRepo).save(goal);
    }

    @Test
    void testAddWeightGoal_UserNotFound() {
        String username = "nonExistentUser";
        WeightGoals goal = new WeightGoals();

        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<WeightGoals> response = weightController.addWeightGoal(username, goal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepo).findByUsername(username);
        verify(weightRepo, never()).save(any(WeightGoals.class));
    }

    @Test
    void testGetWeightList_Success() {
        String username = "testUser";
        UserData user = new UserData();
        user.setUsername(username);
        List<WeightGoals> weightGoals = new ArrayList<>();
        weightGoals.add(new WeightGoals());

        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(weightRepo.findAll()).thenReturn(weightGoals);

        ResponseEntity<List<WeightGoals>> response = weightController.getWeightList(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(weightGoals.size(), response.getBody().size());
        verify(userRepo).findByUsername(username);
        verify(weightRepo).findAll();
    }

    @Test
    void testGetWeightList_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<List<WeightGoals>> response = weightController.getWeightList(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepo).findByUsername(username);
        verify(weightRepo, never()).findAll();
    }*/
}
