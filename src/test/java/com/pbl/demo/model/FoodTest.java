package com.pbl.demo.model;

import com.pbl.demo.model.food.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Food food = new Food();

        // Assert
        assertNotNull(food);
        assertEquals(0, food.getFoodID());
        assertNull(food.getFoodName());
        assertEquals(0.0f, food.getProteins());
        assertEquals(0.0f, food.getCarbs());
        assertEquals(0.0f, food.getFats());
        assertEquals(0.0f, food.getFiber());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        int foodID = 1;
        String foodName = "Apple";
        float proteins = 0.3f;
        float carbs = 14.0f;
        float fats = 0.2f;
        float fiber = 2.4f;

        // Act
        Food food = new Food(foodID, foodName, proteins, carbs, fats, fiber);

        // Assert
        assertEquals(foodID, food.getFoodID());
        assertEquals(foodName, food.getFoodName());
        assertEquals(proteins, food.getProteins());
        assertEquals(carbs, food.getCarbs());
        assertEquals(fats, food.getFats());
        assertEquals(fiber, food.getFiber());

    }

    @Test
    void testConstructorWithoutFoodID() {
        // Arrange
        String foodName = "Banana";
        float proteins = 1.3f;
        float carbs = 27.0f;
        float fats = 0.3f;
        float fiber = 3.1f;

        // Act
        Food food = new Food(foodName, proteins, carbs, fats, fiber);

        // Assert
        assertEquals(foodName, food.getFoodName());
        assertEquals(proteins, food.getProteins());
        assertEquals(carbs, food.getCarbs());
        assertEquals(fats, food.getFats());
        assertEquals(fiber, food.getFiber());

    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Food food = new Food();
        int foodID = 10;
        String foodName = "Orange";
        float proteins = 0.9f;
        float carbs = 12.0f;
        float fats = 0.1f;
        float fiber = 2.2f;

        // Act
        food.setFoodID(foodID);
        food.setFoodName(foodName);
        food.setProteins(proteins);
        food.setCarbs(carbs);
        food.setFats(fats);
        food.setFiber(fiber);

        // Assert
        assertEquals(foodID, food.getFoodID());
        assertEquals(foodName, food.getFoodName());
        assertEquals(proteins, food.getProteins());
        assertEquals(carbs, food.getCarbs());
        assertEquals(fats, food.getFats());
        assertEquals(fiber, food.getFiber());
    }



}
