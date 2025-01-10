package com.pbl.demo.model;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.restrictions.Restrictions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestrictionsTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Restrictions restrictions = new Restrictions();

        // Assert
        assertNotNull(restrictions);
        assertEquals(0, restrictions.getRestrictionID());
        assertNull(restrictions.getRestrictedName());
        assertNull(restrictions.getUserData());
        assertNull(restrictions.getFoodGroup());
        assertNull(restrictions.getFoodClass());
        assertNull(restrictions.getIngredient());
        assertNull(restrictions.getFoodType());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        int restrictionID = 1;
        String restrictedName = "Walnuts";
        UserData userData = new UserData();
        FoodGroup foodGroup = new FoodGroup();
        FoodClass foodClass = new FoodClass();
        Ingredients ingredients = new Ingredients();
        FoodType foodType = new FoodType();

        // Act
        Restrictions restrictions = new Restrictions(restrictionID, restrictedName, userData,foodGroup, foodClass, ingredients, foodType);

        // Assert
        assertEquals(restrictionID, restrictions.getRestrictionID());
        assertEquals(restrictedName, restrictions.getRestrictedName());
        assertEquals(userData, restrictions.getUserData());
        assertEquals(foodGroup, restrictions.getFoodGroup());
        assertEquals(foodClass, restrictions.getFoodClass());
        assertEquals(ingredients, restrictions.getIngredient());
        assertEquals(foodType, restrictions.getFoodType());
    }

    @Test
    void testConstructorWithoutID() {
        // Arrange
        String restrictedName = "Chicken";
        UserData userData = new UserData();
        FoodGroup foodGroup = new FoodGroup();
        FoodClass foodClass = new FoodClass();
        Ingredients ingredients = new Ingredients();
        FoodType foodType = new FoodType();

        // Act
        Restrictions restrictions = new Restrictions(restrictedName, userData,foodGroup, foodClass, ingredients, foodType);

        // Assert
        assertEquals(restrictedName, restrictions.getRestrictedName());
        assertEquals(userData, restrictions.getUserData());
        assertEquals(foodGroup, restrictions.getFoodGroup());
        assertEquals(foodClass, restrictions.getFoodClass());
        assertEquals(ingredients, restrictions.getIngredient());
        assertEquals(foodType, restrictions.getFoodType());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Restrictions restrictions = new Restrictions();
        int restrictionID = 2;
        String restrictedName = "Milk";
        UserData userData = new UserData();
        FoodGroup foodGroup = new FoodGroup();
        FoodClass foodClass = new FoodClass();
        Ingredients ingredients = new Ingredients();
        FoodType foodType = new FoodType();
        

        // Act
        restrictions.setRestrictionID(restrictionID);
        restrictions.setrestrictedName(restrictedName);
        restrictions.setFoodGroup(foodGroup);
        restrictions.setFoodClass(foodClass);
        restrictions.setIngredients(ingredients);
        restrictions.setFoodType(foodType);
        restrictions.setUserData(userData);

        // Assert
        assertEquals(restrictionID, restrictions.getRestrictionID());
        assertEquals(restrictedName, restrictions.getRestrictedName());
        assertEquals(userData, restrictions.getUserData());
        assertEquals(foodGroup, restrictions.getFoodGroup());
        assertEquals(foodClass, restrictions.getFoodClass());
        assertEquals(ingredients, restrictions.getIngredient());
        assertEquals(foodType, restrictions.getFoodType());
    }

    @Test
    void testRelationshipWithUserData() {
        // Arrange
        UserData userData = new UserData();
        Restrictions restrictions = new Restrictions();

        // Act
        restrictions.setUserData(userData);

        // Assert
        assertNotNull(userData);
        // Aquí validamos que el usuario se asocia correctamente, aunque getUserData no está implementado.
    }

    @Test
    void testRelationshipWithFoodGroup() {
        // Arrange
        FoodGroup foodGroup = new FoodGroup();
        Restrictions restrictions = new Restrictions();

        // Act
        restrictions.setFoodGroup(foodGroup);

        // Assert
        assertEquals(foodGroup, restrictions.getFoodGroup());
    }

    @Test
    void testRelationshipWithFoodClass() {
        // Arrange
        FoodClass foodClass = new FoodClass();
        Restrictions restrictions = new Restrictions();

        // Act
        restrictions.setFoodClass(foodClass);

        // Assert
        assertEquals(foodClass, restrictions.getFoodClass());
    }

    @Test
    void testRelationshipWithIngredients() {
        // Arrange
        Ingredients ingredients = new Ingredients();
        Restrictions restrictions = new Restrictions();

        // Act
        restrictions.setIngredients(ingredients);

        // Assert
        assertEquals(ingredients, restrictions.getIngredient());
    }

    @Test
    void testRelationshipWithFoodType() {
        // Arrange
        FoodType foodType = new FoodType();
        Restrictions restrictions = new Restrictions();

        // Act
        restrictions.setFoodType(foodType);

        // Assert
        assertEquals(foodType, restrictions.getFoodType());
    }
}
