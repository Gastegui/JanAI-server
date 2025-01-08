package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.hasIngredients.HasIngredients;
import com.pbl.demo.model.ingredients.Ingredients;

public class HasIngredientsTest {
    @Test
    void testDefaultConstructor(){
        HasIngredients hasIngredients = new HasIngredients();

        assertNotNull(hasIngredients);
        assertNull(hasIngredients.getFood());
        assertNull(hasIngredients.getIngredient());
    }

    @Test
    void testConstructorWithAllParameters(){
        Food food = new Food(1, "Pasta carbonara", 12, 12, 12, 12, null, null);
        Ingredients ingredients = new Ingredients(5, "Eggs");
        HasIngredients hasIngredients = new HasIngredients(food, ingredients);


        assertNotNull(hasIngredients);
        assertEquals(food, hasIngredients.getFood());
        assertEquals(ingredients, hasIngredients.getIngredient());
    }

    @Test
    void testGettersAndSetters(){
        Food food = new Food(1, "Pasta carbonara", 12, 12, 12, 12, null, null);
        Ingredients ingredients = new Ingredients(5, "Eggs");
        HasIngredients hasIngredients = new HasIngredients();

        hasIngredients.setFood(food);
        hasIngredients.setIngredient(ingredients);

        assertEquals(food, hasIngredients.getFood());
        assertEquals(ingredients, hasIngredients.getIngredient());
    }

}
