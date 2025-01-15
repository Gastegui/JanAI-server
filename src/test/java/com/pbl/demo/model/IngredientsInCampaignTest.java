package com.pbl.demo.model;

import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients_in_campaign.IngredientsInCampaign;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class IngredientsInCampaignTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign();

        // Assert
        assertNotNull(ingredientsInCampaign);
        assertNull(ingredientsInCampaign.getCampaign());
        assertNull(ingredientsInCampaign.getIngredients());
        assertNull(ingredientsInCampaign.getInitDate());
        assertNull(ingredientsInCampaign.getEndDate());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        Campaign campaign = new Campaign();
        Ingredients ingredients = new Ingredients();
        Date initDate = new Date();
        Date endDate = new Date();

        // Act
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign(campaign, ingredients, initDate, endDate);

        // Assert
        assertEquals(campaign, ingredientsInCampaign.getCampaign());
        assertEquals(ingredients, ingredientsInCampaign.getIngredients());
        assertEquals(initDate, ingredientsInCampaign.getInitDate());
        assertEquals(endDate, ingredientsInCampaign.getEndDate());
    }

    @Test
    void testConstructorWithPartialParameters() {
        // Arrange
        Date initDate = new Date();
        Date endDate = new Date();

        // Act
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign(initDate, endDate);

        // Assert
        assertNull(ingredientsInCampaign.getCampaign());
        assertNull(ingredientsInCampaign.getIngredients());
        assertEquals(initDate, ingredientsInCampaign.getInitDate());
        assertEquals(endDate, ingredientsInCampaign.getEndDate());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign();
        Campaign campaign = new Campaign();
        Ingredients ingredients = new Ingredients();
        Date initDate = new Date();
        Date endDate = new Date();

        // Act
        ingredientsInCampaign.setCampaign(campaign);
        ingredientsInCampaign.setIngredients(ingredients);
        ingredientsInCampaign.setInitDate(initDate);
        ingredientsInCampaign.setEndDate(endDate);

        // Assert
        assertEquals(campaign, ingredientsInCampaign.getCampaign());
        assertEquals(ingredients, ingredientsInCampaign.getIngredients());
        assertEquals(initDate, ingredientsInCampaign.getInitDate());
        assertEquals(endDate, ingredientsInCampaign.getEndDate());
    }

    @Test
    void testRelationshipWithCampaign() {
        // Arrange
        Campaign campaign = new Campaign();
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign();

        // Act
        ingredientsInCampaign.setCampaign(campaign);

        // Assert
        assertEquals(campaign, ingredientsInCampaign.getCampaign());
    }

    @Test
    void testRelationshipWithIngredients() {
        // Arrange
        Ingredients ingredients = new Ingredients();
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign();

        // Act
        ingredientsInCampaign.setIngredients(ingredients);

        // Assert
        assertEquals(ingredients, ingredientsInCampaign.getIngredients());
    }

    @Test
    void testInitDateAndEndDate() {
        // Arrange
        Date initDate = new Date();
        Date endDate = new Date();
        IngredientsInCampaign ingredientsInCampaign = new IngredientsInCampaign();

        // Act
        ingredientsInCampaign.setInitDate(initDate);
        ingredientsInCampaign.setEndDate(endDate);

        // Assert
        assertEquals(initDate, ingredientsInCampaign.getInitDate());
        assertEquals(endDate, ingredientsInCampaign.getEndDate());
    }
}
