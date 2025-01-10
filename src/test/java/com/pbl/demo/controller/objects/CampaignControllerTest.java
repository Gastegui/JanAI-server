package com.pbl.demo.controller.objects;


import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.campaign.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignControllerTest {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignRepository cmpRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCampaigns_NoCampaigns_ReturnsNotFound() {
        // Arrange
        when(cmpRepo.findAll()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Campaign>> response = campaignController.getAllCampaigns();

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getAllCampaigns_WithCampaigns_ReturnsOk() {
        // Arrange
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(new Campaign());
        when(cmpRepo.findAll()).thenReturn(campaigns);

        // Act
        ResponseEntity<List<Campaign>> response = campaignController.getAllCampaigns();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getCampaignById_NotFound_ReturnsNotFound() {
        // Arrange
        int campaignId = 1;
        when(cmpRepo.findById(campaignId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Campaign> response = campaignController.getCampaignById(campaignId);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getCampaignById_Found_ReturnsOk() {
        // Arrange
        int campaignId = 1;
        Campaign campaign = new Campaign();
        when(cmpRepo.findById(campaignId)).thenReturn(Optional.of(campaign));

        // Act
        ResponseEntity<Campaign> response = campaignController.getCampaignById(campaignId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getCampaignByName_NotFound_ReturnsNotFound() {
        // Arrange
        String campName = "Test Campaign";
        when(cmpRepo.findByCampName(campName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Campaign> response = campaignController.getCampaignByName(campName);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getCampaignByName_Found_ReturnsOk() {
        // Arrange
        String campName = "Test Campaign";
        Campaign campaign = new Campaign();
        when(cmpRepo.findByCampName(campName)).thenReturn(Optional.of(campaign));

        // Act
        ResponseEntity<Campaign> response = campaignController.getCampaignByName(campName);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void addCampaign_ExistingCampaign_ReturnsBadRequest() {
        // Arrange
        Campaign existingCampaign = new Campaign();
        existingCampaign.setCampName("Test Campaign");

        when(cmpRepo.findByCampName(existingCampaign.getCampName())).thenReturn(Optional.of(existingCampaign));

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(existingCampaign);
        
        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void addCampaign_NewCampaign_ReturnsCreated() {
        // Arrange
        Campaign newCampaign = new Campaign();
        newCampaign.setCampName("New Campaign");

        when(cmpRepo.findByCampName(newCampaign.getCampName())).thenReturn(Optional.empty());
        when(cmpRepo.save(newCampaign)).thenReturn(newCampaign);

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(newCampaign);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}
