package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;
import com.pbl.demo.model.food_list.FoodList;
import com.pbl.demo.model.food_list.FoodListRepository;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.*;

class FoodListControllerTest {

    @Mock
    private FoodListRepository foodListRepo;

    @Mock
    private FoodRepository foodRepo;

    @Mock
    private UserDataRepository userDataRepo;

    @InjectMocks
    private FoodListController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMacrosByUser_NoFoodFound() {
        // Arrange
        int userID = 1;
        when(foodListRepo.findByUserId(userID)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<Map<String, Double>> response = controller.getMacrosByUser(userID);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetMacrosByUser_FoodFound() {
        // Arrange
        int userID = 1;
        LocalDate today = LocalDate.now();

        Food food = new Food();
        food.setProteins(0.1f);
        food.setCarbs(0.2f);
        food.setFats(0.3f);
        food.setFiber(0.4f);
        food.setCalories(0.5f);

        FoodList foodList = new FoodList();
        foodList.setFood(food);
        foodList.setGrams(100f);
        foodList.setConsumptionDate(today);

        when(foodListRepo.findByUserId(userID)).thenReturn(List.of(foodList));

        // Act
        ResponseEntity<Map<String, Double>> response = controller.getMacrosByUser(userID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Double> macros = response.getBody();
        assertNotNull(macros);
        assertEquals(0.1, macros.get("totalProteins"));
        assertEquals(0.2, macros.get("totalCarbs"));
        assertEquals(0.3, macros.get("totalFats"));
        assertEquals(0.4, macros.get("totalFiber"));
        assertEquals(0.5, macros.get("totalCalories"));
    }

    @Test
    void testAddFoodList_FoodOrUserNotFound() {
        // Arrange
        int foodID = 1;
        int userID = 1;
        BindingResult result = mock(BindingResult.class);

        when(foodRepo.findById(foodID)).thenReturn(Optional.empty());
        when(userDataRepo.findById(userID)).thenReturn(Optional.empty());

        FoodList foodList = new FoodList();

        // Act
        ResponseEntity<FoodList> response = controller.addFoodList(foodID, userID, foodList, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAddFoodList_Success() {
        // Arrange
        int foodID = 1;
        int userID = 1;

        Food food = new Food();
        UserData userData = new UserData();
        BindingResult result = mock(BindingResult.class);
        when(foodRepo.findById(foodID)).thenReturn(Optional.of(food));
        when(userDataRepo.findById(userID)).thenReturn(Optional.of(userData));

        FoodList foodList = new FoodList();

        // Act
        ResponseEntity<FoodList> response = controller.addFoodList(foodID, userID, foodList, result);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(food, foodList.getFood());
        assertEquals(userData, foodList.getUserData());
        verify(foodListRepo, times(1)).save(foodList);
    }
}
