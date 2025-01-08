package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.foodGroup.FoodGroup;

public class FoodGroupTest {
    @Test
    void testDefaultConstructor(){
        FoodGroup fGroup = new FoodGroup();

        assertNotNull(fGroup);
        assertEquals(0, fGroup.getGroupID());
        assertNull(fGroup.getGroupName());
        assertNull(fGroup.getFoodType());
    }

    @Test
    void testConstructorWithAllParameters(){
        int id = 4;
        String name = "chicken";
        FoodGroup fGroup = new FoodGroup(id, name);
        

        assertNotNull(fGroup);
        assertEquals(id, fGroup.getGroupID());
        assertEquals(name, fGroup.getGroupName());
    }

    @Test
    void testConstructorWithoutId(){
        String name = "chicken";
        FoodGroup fGroup = new FoodGroup(name);
        

        assertNotNull(fGroup);
        assertEquals(name, fGroup.getGroupName());
    }

    @Test
    void testGettersAndSetters(){
        int id = 4;
        String name = "chicken";
        FoodGroup fGroup = new FoodGroup();
        
        fGroup.setGroupID(id);
        fGroup.setGroupName(name);

        assertNotNull(fGroup);
        assertEquals(id, fGroup.getGroupID());
        assertEquals(name, fGroup.getGroupName());
    }
}
