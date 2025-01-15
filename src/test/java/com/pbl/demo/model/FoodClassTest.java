package com.pbl.demo.model;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.food_class.FoodClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class FoodClassTest {
    @Test
    void testDefaultConstructor(){
        FoodClass fClass = new FoodClass();

        assertNotNull(fClass);
        assertEquals(0, fClass.getClassID());
        assertNull(fClass.getClassName());
    }

    @Test
    void testConstructorWithAllParameters(){
        int id = 4;
        String name = "meat";
        FoodClass fClass = new FoodClass(id, name);

        assertNotNull(fClass);
        assertEquals(id, fClass.getClassID());
        assertEquals(name, fClass.getClassName());
    }

    @Test
    void testConstructorWithoutId(){
        String name = "meat";
        FoodClass fClass = new FoodClass(name);

        assertNotNull(fClass);
        assertEquals(name, fClass.getClassName());
    }

    @Test
    void testGettersAndSetters(){
        int id = 4;
        String name = "meat";
        FoodClass fClass = new FoodClass();

        fClass.setClassID(id);
        fClass.setClassName(name);

        assertEquals(name, fClass.getClassName());
        assertEquals(id, fClass.getClassID());
    }
}
