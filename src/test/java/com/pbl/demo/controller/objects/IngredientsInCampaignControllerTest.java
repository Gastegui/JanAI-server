package com.pbl.demo.controller.objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.campaign.CampaignRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.ingredients_in_campaign.IngredientsInCampaign;
import com.pbl.demo.model.ingredients_in_campaign.IngredientsInCampaignRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class IngredientsInCampaignControllerTest {

    @Mock
    private CampaignRepository cmpRepo;

    @Mock
    private IngredientsInCampaignRepository ingCmpRepo;

    @Mock
    private IngredientsRepository ingRepo;

    @Mock
    private BindingResult result;

    @InjectMocks
    private IngredientsInCampaignController controller;

    public IngredientsInCampaignControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIngredientsInCampaign_Success() {
        List<IngredientsInCampaign> mockList = new ArrayList<>();
        mockList.add(new IngredientsInCampaign());
        mockList.add(new IngredientsInCampaign());

        when(ingCmpRepo.findAll()).thenReturn(mockList);

        ResponseEntity<List<IngredientsInCampaign>> response = controller.getAllIngredientsInCampaign();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(ingCmpRepo).findAll();
    }

    @Test
    void testGetAllIngredientsInCampaign_NotFound() {
        when(ingCmpRepo.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<IngredientsInCampaign>> response = controller.getAllIngredientsInCampaign();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(ingCmpRepo).findAll();
    }


    @Test
    void testAddIngredientInCampaign_Duplicate() {
        int campaignID = 1;
        int ingredientID = 10;

        Campaign campaign = new Campaign();
        campaign.setCampaignID(campaignID);

        Ingredients ingredient = new Ingredients();
        ingredient.setIngredientID(ingredientID);

        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();

        when(cmpRepo.findById(campaignID)).thenReturn(Optional.of(campaign));
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.of(ingredient));
        when(ingCmpRepo.findIngredientsInCampaignByCampaignIDAndIngredientID(campaignID, ingredientID)).thenReturn(true);

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(cmpRepo).findById(campaignID);
        verify(ingRepo).findById(ingredientID);
        verify(ingCmpRepo).findIngredientsInCampaignByCampaignIDAndIngredientID(campaignID, ingredientID);
        verify(ingCmpRepo, never()).save(any());
    }

    @Test
    void testAddIngredientInCampaign_Error() {
        int campaignID = 1;
        int ingredientID = 10;

        when(cmpRepo.findById(campaignID)).thenReturn(Optional.empty());
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.empty());

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, new IngredientsInCampaign(), result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(cmpRepo).findById(campaignID);
        verify(ingRepo).findById(ingredientID);
        verify(ingCmpRepo, never()).save(any());
    }

    @Test
    void testAddIngredientInCampaign_Error_OneOptionalMissing() {
        int campaignID = 1;
        int ingredientID = 10;
        Ingredients ingredient = new Ingredients();
        ingredient.setIngredientID(ingredientID);
        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();

        when(cmpRepo.findById(campaignID)).thenReturn(Optional.empty());
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.of(ingredient));

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(cmpRepo).findById(campaignID);
        verify(ingRepo).findById(ingredientID);
        verify(ingCmpRepo, never()).save(any());
    }

    @Test
    void testAddIngredientInCampaign_Error_OtherOptionalMissing() {
        int campaignID = 1;
        int ingredientID = 10;
        Campaign campaign = new Campaign();
        campaign.setCampaignID(campaignID);
        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();

        when(cmpRepo.findById(campaignID)).thenReturn(Optional.of(campaign));
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.empty());

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(cmpRepo).findById(campaignID);
        verify(ingRepo).findById(ingredientID);
        verify(ingCmpRepo, never()).save(any());
    }

    @Test
    void testAddIngredientInCampaign_ValidationErrors() {
        // Arrange
        int campaignID = 1;
        int ingredientID = 1;
        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();
        when(result.hasErrors()).thenReturn(true);

        // Act
        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Validation-Error"));
    }

    @Test
    void testAddIngredientInCampaign_CampaignOrIngredientNotFound() {
        // Arrange
        int campaignID = 1;
        int ingredientID = 1;
        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();
        
        when(result.hasErrors()).thenReturn(false);
        when(cmpRepo.findById(campaignID)).thenReturn(Optional.empty());
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddIngredientInCampaign_IngredientAlreadyInCampaign() {
        // Arrange
        int campaignID = 1;
        int ingredientID = 1;
        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();
        Campaign campaign = new Campaign();
        Ingredients ingredient = new Ingredients();
        campaign.setCampaignID(campaignID);
        ingredient.setIngredientID(ingredientID);

        when(result.hasErrors()).thenReturn(false);
        when(cmpRepo.findById(campaignID)).thenReturn(Optional.of(campaign));
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.of(ingredient));
        when(ingCmpRepo.findIngredientsInCampaignByCampaignIDAndIngredientID(campaignID, ingredientID)).thenReturn(true);

        // Act
        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddIngredientInCampaign_Success() {
        // Arrange
        int campaignID = 1;
        int ingredientID = 1;
        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();
        Campaign campaign = new Campaign();
        Ingredients ingredient = new Ingredients();
        when(result.hasErrors()).thenReturn(false);
        when(cmpRepo.findById(campaignID)).thenReturn(Optional.of(campaign));
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.of(ingredient));
        when(ingCmpRepo.findIngredientsInCampaignByCampaignIDAndIngredientID(campaignID, ingredientID)).thenReturn(false);

        // Act
        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign, result);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ingredientCampaign, response.getBody());
        verify(ingCmpRepo, times(1)).save(ingredientCampaign);
        assertEquals(campaign, ingredientCampaign.getCampaign());
        assertEquals(ingredient, ingredientCampaign.getIngredients());
    }
}
