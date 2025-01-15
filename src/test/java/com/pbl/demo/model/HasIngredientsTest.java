package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.has_ingredients.HasIngredients;
import com.pbl.demo.model.ingredients.Ingredients;

class HasIngredientsTest {
    @Test
    void testDefaultConstructor(){
        HasIngredients hasIngredients = new HasIngredients();

        assertNotNull(hasIngredients);
        assertNull(hasIngredients.getFood());
        assertNull(hasIngredients.getIngredient());
    }

    @Test
    void testConstructorWithAllParameters(){
        Food food = new Food(1, "Pasta bolognese", 12f, 34f, 6f, 9f, 50f);
        Ingredients ingredients = new Ingredients(1, "Tomato", new FoodGroup());
        HasIngredients hasIngredients = new HasIngredients(food, ingredients);


        assertNotNull(hasIngredients);
        assertEquals(food, hasIngredients.getFood());
        assertEquals(ingredients, hasIngredients.getIngredient());
    }

    @Test
    void testGettersAndSetters(){
        Food food = new Food(1, "Pasta bolognese", 12f, 34f, 6f, 9f, 50f);
        Ingredients ingredients = new Ingredients(1, "Tomato", new FoodGroup());
        HasIngredients hasIngredients = new HasIngredients();

        hasIngredients.setFood(food);
        hasIngredients.setIngredient(ingredients);

        assertEquals(food, hasIngredients.getFood());
        assertEquals(ingredients, hasIngredients.getIngredient());
    }

}
