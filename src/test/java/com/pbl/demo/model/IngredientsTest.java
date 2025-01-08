package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.ingredients.Ingredients;

public class IngredientsTest {
    @Test
    void testDefaultConstructor(){
        Ingredients ing = new Ingredients();

        assertNotNull(ing);
        assertEquals(0, ing.getIngredientID());
        assertNull(ing.getIngName());
    }

    @Test
    void testConstructorWithAllParameters(){
        int ingId = 8;
        String name = "Tomato";

        Ingredients ing = new Ingredients(ingId, name);

        assertEquals(name, ing.getIngName());
        assertEquals(ingId, ing.getIngredientID());
    }

    @Test
    void testConstructorWithoutId(){
        String name = "Tomato";

        Ingredients ing = new Ingredients(name);

        assertEquals(name, ing.getIngName());
    }

    @Test
    void testGettersAndSetters(){
        Ingredients ing = new Ingredients();
        int ingId = 8;
        String name = "Tomato";

        ing.setIngName(name);
        ing.setIngredientID(ingId);

        assertEquals(name, ing.getIngName());
        assertEquals(ingId, ing.getIngredientID());
    }
}
