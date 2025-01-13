package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.foodList.FoodList;
import com.pbl.demo.model.foodList.FoodListRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FoodListControllerTest {

    @Mock
    private FoodListRepository foodListRepo;

    @InjectMocks
    private FoodListController foodListController;

    public FoodListControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMacrosByUser_NotFound() {
        int userId = 999;

        when(foodListRepo.findByUserId(userId)).thenReturn(new ArrayList<>());

        ResponseEntity<Map<String, Double>> response = foodListController.getMacrosByUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(foodListRepo).findByUserId(userId);
    }

    @Test
    void testGetMacrosByUser_Success() {
        int userId = 1;

        // Mocking food entries
        Food food1 = new Food(1, "Txuleta", 10.0f, 20.0f, 5.0f, 3.0f, 150.0f); // Proteins, Carbs, Fats, Fiber, Calories
        Food food2 = new Food(2, "Cod", 15.5f, 30.0f, 10.0f, 2.0f, 200.0f);

        FoodList entry1 = new FoodList();
        entry1.setFood(food1);

        FoodList entry2 = new FoodList();
        entry2.setFood(food2);

        List<FoodList> mockFoodList = List.of(entry1, entry2);

        when(foodListRepo.findByUserId(userId)).thenReturn(mockFoodList);

        ResponseEntity<Map<String, Double>> response = foodListController.getMacrosByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Double> macros = response.getBody();
        assertNotNull(macros);
        assertEquals(25.5, macros.get("totalProteins")); // 10.0 + 15.5
        assertEquals(50.0, macros.get("totalCarbs")); // 20.0 + 30.0
        assertEquals(15.0, macros.get("totalFats")); // 5.0 + 10.0
        assertEquals(5.0, macros.get("totalFiber")); // 3.0 + 2.0
        assertEquals(350.0, macros.get("totalCalories")); // 150.0 + 200.0

        verify(foodListRepo).findByUserId(userId);
    }


}
