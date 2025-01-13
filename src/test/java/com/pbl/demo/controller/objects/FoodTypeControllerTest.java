package com.pbl.demo.controller.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.food_type.FoodType;
import com.pbl.demo.model.food_type.FoodTypeRepository;

public class FoodTypeControllerTest {
     @InjectMocks
    private FoodTypeController foodTypeController;

    @Mock
    private FoodTypeRepository ftRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTypes_NoTypes_ReturnsNotFound() {
        // Arrange
        when(ftRepo.findAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<FoodType>> response = foodTypeController.getAllTypes();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllTypes_TypesExist_Exist_ReturnsOk() {
        // Arrange
        List<FoodType> groups = List.of(new FoodType());
        when(ftRepo.findAll()).thenReturn(groups);

        // Act
        ResponseEntity<List<FoodType>> response = foodTypeController.getAllTypes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groups, response.getBody());
    }

    @Test
    void getTypeByTypeName_GroupNotFound_ReturnsNotFound() {
        // Arrange
        String typeName = "example";
        when(ftRepo.findByTypeName(typeName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<FoodType> response = foodTypeController.getTypeByTypeName(typeName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getTypeByTypeName_TypeFound_ReturnsOk() {
        // Arrange
        String typeName = "Meat";
        FoodType foodType = new FoodType();
        when(ftRepo.findByTypeName(typeName)).thenReturn(Optional.of(foodType));

        // Act
        ResponseEntity<FoodType> response = foodTypeController.getTypeByTypeName(typeName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodType, response.getBody());
    }
}
