package com.pbl.demo.controller.objects;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.campaign.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignControllerTest {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignRepository cmpRepo;

    @Mock
    private AdministratorRepository adminRepo;

    @Mock
    private BindingResult result;

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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void addCampaign_ExistingCampaign_ReturnsBadRequest() {
        // Arrange
        Campaign existingCampaign = new Campaign();
        existingCampaign.setCampName("Test Campaign");
        int adminId = 1;

        when(cmpRepo.findByCampName(existingCampaign.getCampName())).thenReturn(Optional.of(existingCampaign));

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(adminId, existingCampaign, result);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addCampaign_NewCampaign_ReturnsCreated() {
        // Arrange
        int adminId = 1;
        Administrator admin = new Administrator();
        Campaign newCampaign = new Campaign(4, "Bergara", "Etxeko ogi salmenta","Erein da Jan", admin);
        

        when(cmpRepo.findByCampName(newCampaign.getCampName())).thenReturn(Optional.empty());
        when(cmpRepo.save(newCampaign)).thenReturn(newCampaign);
        when(adminRepo.findById(adminId)).thenReturn(Optional.of(admin));

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(adminId, newCampaign, result);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddCampaign_ValidationErrors() {
        // Arrange
        int adminID = 1;
        Campaign campaign = new Campaign();
        when(result.hasErrors()).thenReturn(true);

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(adminID, campaign, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Validation-Error"));
    }

    @Test
    void testAddCampaign_DuplicateCampaignName() {
        // Arrange
        int adminID = 1;
        Campaign campaign = new Campaign();
        campaign.setCampName("Duplicate Name");
        
        when(result.hasErrors()).thenReturn(false);
        when(cmpRepo.findByCampName(campaign.getCampName())).thenReturn(Optional.of(new Campaign()));

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(adminID, campaign, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddCampaign_AdminNotFound() {
        // Arrange
        int adminID = 1;
        Campaign campaign = new Campaign();
        campaign.setCampName("Unique Name");
        
        when(result.hasErrors()).thenReturn(false);
        when(cmpRepo.findByCampName(campaign.getCampName())).thenReturn(Optional.empty());
        when(adminRepo.findById(adminID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(adminID, campaign, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddCampaign_Success() {
        // Arrange
        int adminID = 1;
        Campaign campaign = new Campaign();
        campaign.setCampName("Unique Name");
        Administrator admin = new Administrator();
        
        when(result.hasErrors()).thenReturn(false);
        when(cmpRepo.findByCampName(campaign.getCampName())).thenReturn(Optional.empty());
        when(adminRepo.findById(adminID)).thenReturn(Optional.of(admin));

        // Act
        ResponseEntity<Campaign> response = campaignController.addCampaign(adminID, campaign, result);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(campaign, response.getBody());
        verify(cmpRepo, times(1)).save(campaign);
        assertEquals(admin, campaign.getAdministrator());
    }
}
