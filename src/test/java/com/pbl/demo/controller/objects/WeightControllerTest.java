package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.model.weightGoals.WeightGoals;

@WebMvcTest(controllers = WeightController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WeightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserDataRepository userRepo;

    @InjectMocks
    private WeightController weightController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddWeightGoal_UserExists_ShouldReturnCreated() throws Exception {
        // Datos de prueba
        String username = "testUser";
        WeightGoals goal = new WeightGoals();
        goal.setGoalWeight(new Float(70));
        UserData user = new UserData();
        user.setUsername(username);

        // Configuración del mock
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));

        // Acción
        ResultActions response = mockMvc.perform(post("/weight/addWeight")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(goal)));

        // Verificación
        response.andExpect(status().isCreated());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testAddWeightGoal_UserNotFound_ShouldReturnBadRequest() throws Exception {
        // Datos de prueba
        String username = "nonExistentUser";
        WeightGoals goal = new WeightGoals();
        goal.setGoalWeight(new Float(75.0));

        // Configuración del mock
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Acción
        ResultActions response = mockMvc.perform(post("/weight/addWeight")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(goal)));

        // Verificación
        response.andExpect(status().isBadRequest());
        verify(userRepo, never()).save(any(UserData.class));
    }

    @Test
    void testGetWeightList_UserExists_ShouldReturnWeightList() throws Exception {
        // Datos de prueba
        String username = "testUser";
        UserData user = new UserData();
        user.setUsername(username);
        List<WeightGoals> weightGoalsList = new ArrayList<>();
        WeightGoals goal1 = new WeightGoals();
        goal1.setGoalWeight(new Float(65.0));
        WeightGoals goal2 = new WeightGoals();
        goal2.setGoalWeight(new Float(70.0));
        weightGoalsList.add(goal1);
        weightGoalsList.add(goal2);
        user.setWeightGoals(weightGoalsList);

        // Configuración del mock
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));

        // Acción
        ResultActions response = mockMvc.perform(get("/weight/weightList")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON));

        // Verificación
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetWeightList_UserNotFound_ShouldReturnNotFound() throws Exception {
        // Datos de prueba
        String username = "nonExistentUser";

        // Configuración del mock
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Acción
        ResultActions response = mockMvc.perform(get("/weight/weightList")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON));

        // Verificación
        response.andExpect(status().isNotFound());
    }
}

