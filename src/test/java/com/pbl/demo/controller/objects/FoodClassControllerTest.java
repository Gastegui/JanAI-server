package com.pbl.demo.controller.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodClass.FoodClassRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodClassControllerTest {

    @InjectMocks
    private FoodClassController foodClassController;

    @Mock
    private FoodClassRepository fcRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClasses_NoClasses_ReturnsNotFound() {
        // Arrange
        when(fcRepo.findAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<FoodClass>> response = foodClassController.getAllClasses();

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getAllClasses_ClassesExist_ReturnsOk() {
        // Arrange
        List<FoodClass> classes = List.of(new FoodClass());
        when(fcRepo.findAll()).thenReturn(classes);

        // Act
        ResponseEntity<List<FoodClass>> response = foodClassController.getAllClasses();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(classes, response.getBody());
    }

    @Test
    void getGroupByGroupName_ClassNotFound_ReturnsNotFound() {
        // Arrange
        String className = "Vegetables";
        when(fcRepo.findByClassName(className)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<FoodClass> response = foodClassController.getGroupByGroupName(className);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getGroupByGroupName_ClassFound_ReturnsOk() {
        // Arrange
        String className = "Vegetables";
        FoodClass foodClass = new FoodClass();
        when(fcRepo.findByClassName(className)).thenReturn(Optional.of(foodClass));

        // Act
        ResponseEntity<FoodClass> response = foodClassController.getGroupByGroupName(className);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(foodClass, response.getBody());
    }
}
