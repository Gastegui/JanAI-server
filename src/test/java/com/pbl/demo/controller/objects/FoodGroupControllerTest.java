package com.pbl.demo.controller.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.food_group.FoodGroupRepository;

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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodGroup, response.getBody());
    }
}
