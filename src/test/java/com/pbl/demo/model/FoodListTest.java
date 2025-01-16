package com.pbl.demo.model;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food_list.FoodList;
import com.pbl.demo.model.user_data.UserData;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FoodListTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        FoodList foodList = new FoodList();

        // Assert
        assertNotNull(foodList);
        assertNull(foodList.getUserData());
        assertNull(foodList.getFood());
        assertNull(foodList.getConsumptionDate());
        assertNull(foodList.getMeal());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        UserData userData = new UserData();
        Food food = new Food();
        Date consumptionDate = new Date();
        String meal = "Breakfast";
        Float grams = 100.0f;

        // Act
        FoodList foodList = new FoodList(userData, food, consumptionDate, meal, grams);

        // Assert
        assertEquals(userData, foodList.getUserData());
        assertEquals(food, foodList.getFood());
        assertEquals(consumptionDate, foodList.getConsumptionDate());
        assertEquals(meal, foodList.getMeal());
    }

    @Test
    void testConstructorWithPartialParameters() {
        // Arrange
        Date consumptionDate = new Date();
        String meal = "Dinner";
        Float grams = 200.0f;

        // Act
        FoodList foodList = new FoodList(consumptionDate, meal, grams);

        // Assert
        assertNull(foodList.getUserData());
        assertNull(foodList.getFood());
        assertEquals(consumptionDate, foodList.getConsumptionDate());
        assertEquals(meal, foodList.getMeal());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        FoodList foodList = new FoodList();
        UserData userData = new UserData();
        Food food = new Food();
        Date consumptionDate = new Date();
        String meal = "Lunch";

        // Act
        foodList.setUserData(userData);
        foodList.setFood(food);
        foodList.setConsumptionDate(consumptionDate);
        foodList.setMeal(meal);

        // Assert
        assertEquals(userData, foodList.getUserData());
        assertEquals(food, foodList.getFood());
        assertEquals(consumptionDate, foodList.getConsumptionDate());
        assertEquals(meal, foodList.getMeal());
    }

    @Test
    void testRelationshipWithUserData() {
        // Arrange
        UserData userData = new UserData();
        FoodList foodList = new FoodList();

        // Act
        foodList.setUserData(userData);

        // Assert
        assertEquals(userData, foodList.getUserData());
    }

    @Test
    void testRelationshipWithFood() {
        // Arrange
        Food food = new Food();
        FoodList foodList = new FoodList();

        // Act
        foodList.setFood(food);

        // Assert
        assertEquals(food, foodList.getFood());
    }

    @Test
    void testConsumptionDateAndMeal() {
        // Arrange
        Date consumptionDate = new Date();
        String meal = "Snack";
        FoodList foodList = new FoodList();

        // Act
        foodList.setConsumptionDate(consumptionDate);
        foodList.setMeal(meal);

        // Assert
        assertEquals(consumptionDate, foodList.getConsumptionDate());
        assertEquals(meal, foodList.getMeal());
    }
}
