package com.pbl.demo.controller.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodControllerTest {

    @InjectMocks
    private FoodController foodController;

    @Mock
    private FoodRepository foodRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFoods_NoFoods_ReturnsNotFound() {
        // Arrange
        when(foodRepo.findAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<Food>> response = foodController.getFoods();

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getFoods_FoodsExist_ReturnsOk() {
        // Arrange
        List<Food> foods = List.of(new Food());
        when(foodRepo.findAll()).thenReturn(foods);

        // Act
        ResponseEntity<List<Food>> response = foodController.getFoods();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(foods, response.getBody());
    }

    @Test
    void addFood_FoodAlreadyExists_ReturnsBadRequest() {
        // Arrange
        Food food = new Food();
        food.setFoodName("Pizza");
        when(foodRepo.findByFoodName("Pizza")).thenReturn(Optional.of(food));

        // Act
        ResponseEntity<Food> response = foodController.addFood(food);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void addFood_NewFood_ReturnsCreated() {
        // Arrange
        Food food = new Food();
        food.setFoodName("Burger");
        when(foodRepo.findByFoodName("Burger")).thenReturn(Optional.empty());
        when(foodRepo.save(food)).thenReturn(food);

        // Act
        ResponseEntity<Food> response = foodController.addFood(food);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(food, response.getBody());
    }

    @Test
    void findById_FoodNotFound_ReturnsNotFound() {
        // Arrange
        int id = 1;
        when(foodRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Food> response = foodController.findById(id);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void findById_FoodFound_ReturnsOk() {
        // Arrange
        int id = 1;
        Food food = new Food();
        when(foodRepo.findById(id)).thenReturn(Optional.of(food));

        // Act
        ResponseEntity<Food> response = foodController.findById(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(food, response.getBody());
    }

    @Test
    void findByName_FoodNotFound_ReturnsNotFound() {
        // Arrange
        String foodName = "Pasta";
        when(foodRepo.findByFoodName(foodName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Food> response = foodController.findByName(foodName);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void findByName_FoodFound_ReturnsOk() {
        // Arrange
        String foodName = "Sushi";
        Food food = new Food();
        when(foodRepo.findByFoodName(foodName)).thenReturn(Optional.of(food));

        // Act
        ResponseEntity<Food> response = foodController.findByName(foodName);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(food, response.getBody());
    }
}

