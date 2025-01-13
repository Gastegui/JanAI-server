package com.pbl.demo.controller.objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.campaign.CampaignRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.ingredientsInCampaign.IngredientsInCampaign;
import com.pbl.demo.model.ingredientsInCampaign.IngredientsInCampaignRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void testAddIngredientInCampaign_Success() {
        int campaignID = 1;
        int ingredientID = 10;

        Campaign campaign = new Campaign();
        campaign.setCampaignID(campaignID);

        Ingredients ingredient = new Ingredients();
        ingredient.setIngredientID(ingredientID);

        IngredientsInCampaign ingredientCampaign = new IngredientsInCampaign();

        when(cmpRepo.findById(campaignID)).thenReturn(Optional.of(campaign));
        when(ingRepo.findById(ingredientID)).thenReturn(Optional.of(ingredient));
        when(ingCmpRepo.findIngredientsInCampaignByCampaignIDAndIngredientID(campaignID, ingredientID)).thenReturn(false);

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(campaign, ingredientCampaign.getCampaign());
        assertEquals(ingredient, ingredientCampaign.getIngredients());

        verify(cmpRepo).findById(campaignID);
        verify(ingRepo).findById(ingredientID);
        verify(ingCmpRepo).findIngredientsInCampaignByCampaignIDAndIngredientID(campaignID, ingredientID);
        verify(ingCmpRepo).save(ingredientCampaign);
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

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign);

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

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, new IngredientsInCampaign());

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

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign);

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

        ResponseEntity<IngredientsInCampaign> response = controller.addIngredientInCampaign(campaignID, ingredientID, ingredientCampaign);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(cmpRepo).findById(campaignID);
        verify(ingRepo).findById(ingredientID);
        verify(ingCmpRepo, never()).save(any());
    }
}
