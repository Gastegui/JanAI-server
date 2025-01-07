package com.pbl.demo.controller.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodGroup.FoodGroupRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodGroupControllerTest {

    @InjectMocks
    private FoodGroupController foodGroupController;

    @Mock
    private FoodGroupRepository fgRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGroups_NoGroups_ReturnsNotFound() {
        // Arrange
        when(fgRepo.findAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<FoodGroup>> response = foodGroupController.getAllGroups();

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getAllGroups_GroupsExist_ReturnsOk() {
        // Arrange
        List<FoodGroup> groups = List.of(new FoodGroup());
        when(fgRepo.findAll()).thenReturn(groups);

        // Act
        ResponseEntity<List<FoodGroup>> response = foodGroupController.getAllGroups();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(groups, response.getBody());
    }

    @Test
    void getGroupByGroupName_GroupNotFound_ReturnsNotFound() {
        // Arrange
        String groupName = "Fruits";
        when(fgRepo.findByGroupName(groupName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<FoodGroup> response = foodGroupController.getGroupByGroupName(groupName);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getGroupByGroupName_GroupFound_ReturnsOk() {
        // Arrange
        String groupName = "Fruits";
        FoodGroup foodGroup = new FoodGroup();
        when(fgRepo.findByGroupName(groupName)).thenReturn(Optional.of(foodGroup));

        // Act
        ResponseEntity<FoodGroup> response = foodGroupController.getGroupByGroupName(groupName);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(foodGroup, response.getBody());
    }
}
