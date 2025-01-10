package com.pbl.demo.model;

import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.ingredients.Ingredients;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientsTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Ingredients ingredient = new Ingredients();

        // Assert
        assertNotNull(ingredient);
        assertEquals(0, ingredient.getIngredientID());
        assertNull(ingredient.getIngName());
        assertNull(ingredient.getGroupID());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        int ingredientID = 1;
        String ingName = "Tomato";
        FoodGroup foodGroup = new FoodGroup();

        // Act
        Ingredients ingredient = new Ingredients(ingredientID, ingName, foodGroup);

        // Assert
        assertEquals(ingredientID, ingredient.getIngredientID());
        assertEquals(ingName, ingredient.getIngName());
        assertEquals(foodGroup, ingredient.getGroupID());
    }

    @Test
    void testConstructorWithPartialParameters() {
        // Arrange
        String ingName = "Lettuce";
        FoodGroup foodGroup = new FoodGroup();

        // Act
        Ingredients ingredient = new Ingredients(ingName, foodGroup);

        // Assert
        assertEquals(0, ingredient.getIngredientID());
        assertEquals(ingName, ingredient.getIngName());
        assertEquals(foodGroup, ingredient.getGroupID());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Ingredients ingredient = new Ingredients();
        int ingredientID = 2;
        String ingName = "Carrot";
        FoodGroup foodGroup = new FoodGroup();

        // Act
        ingredient.setIngredientID(ingredientID);
        ingredient.setIngName(ingName);
        ingredient.setGroupID(foodGroup);

        // Assert
        assertEquals(ingredientID, ingredient.getIngredientID());
        assertEquals(ingName, ingredient.getIngName());
        assertEquals(foodGroup, ingredient.getGroupID());
    }

    
    @Test
    void testSettersWithNullValues() {
        // Arrange
        Ingredients ingredient = new Ingredients();

        // Act
        ingredient.setIngName(null);
        ingredient.setGroupID(null);

        // Assert
        assertNull(ingredient.getIngName());
        assertNull(ingredient.getGroupID());
    }



    @Test
    void testExtremeValues() {
        // Arrange
        Ingredients ingredient = new Ingredients();
        int extremeID = Integer.MAX_VALUE;

        // Act
        ingredient.setIngredientID(extremeID);

        // Assert
        assertEquals(extremeID, ingredient.getIngredientID());
    }

    
}
