package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.food_type.FoodType;

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
        FoodType fType = new FoodType();
        FoodGroup fGroup = new FoodGroup(id, name, fType);
        

        assertNotNull(fGroup);
        assertEquals(id, fGroup.getGroupID());
        assertEquals(name, fGroup.getGroupName());
        assertEquals(fType, fGroup.getFoodType());
    }

    @Test
    void testConstructorWithoutId(){
        String name = "chicken";
        FoodType fType = new FoodType();
        FoodGroup fGroup = new FoodGroup(name, fType);
        

        assertNotNull(fGroup);
        assertEquals(name, fGroup.getGroupName());
        assertEquals(fType, fGroup.getFoodType());
    }

    @Test
    void testGettersAndSetters(){
        int id = 4;
        String name = "chicken";
        FoodType fType = new FoodType();
        FoodGroup fGroup = new FoodGroup();
        
        fGroup.setGroupID(id);
        fGroup.setGroupName(name);
        fGroup.setFoodType(fType);

        assertNotNull(fGroup);
        assertEquals(id, fGroup.getGroupID());
        assertEquals(name, fGroup.getGroupName());
        assertEquals(fType, fGroup.getFoodType());
    }
}
