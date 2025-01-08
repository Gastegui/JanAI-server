package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodType.FoodType;

public class FoodTypeTest {
    @Test
    void testDefaultConstructor(){
        FoodType fType = new FoodType();

        assertNotNull(fType);
        assertEquals(0, fType.getTypeId());
        assertNull(fType.getTypeName());
        assertNull(fType.getFoodClass());
    }

    @Test
    void testConstructorWithAllParameters(){
        int id = 7;
        String name = "poultry";
        FoodType fType = new FoodType(id, name);
        

        assertNotNull(fType);
        assertEquals(id, fType.getTypeId());
        assertEquals(name, fType.getTypeName());
    }

    @Test
    void testConstructorWithoutId(){
        String name = "chicken";
        FoodType fType = new FoodType(name);
        
        assertNotNull(fType);
        assertEquals(name, fType.getTypeName());
    }

    @Test
    void testGettersAndSetters(){
        int id = 4;
        String name = "chicken";
        FoodType fType = new FoodType();
        FoodClass fClass = new FoodClass(1,"Animal Origin");
        
        fType.setTypeId(id);
        fType.setTypeName(name);
        fType.setFoodClass(fClass);

        assertNotNull(fType);
        assertEquals(id, fType.getTypeId());
        assertEquals(name, fType.getTypeName());
        assertEquals(fClass, fType.getFoodClass());
    }
}
