package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodClass.FoodClassRepository;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodGroup.FoodGroupRepository;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.foodType.FoodTypeRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.restrictions.RestrictionsRepository;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AutoConfigureMockMvc
class RestrictionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RestrictionsController restrictionsController;

    @Mock
    private RestrictionsRepository restrictRepo;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private FoodClassRepository foodClassRepo;

    @Mock
    private FoodTypeRepository foodTypeRepo;

    @Mock
    private FoodGroupRepository foodGroupRepo;

    @Mock
    private IngredientsRepository ingredienRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestrictions_NoRestrictionsFound() {
        when(restrictRepo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Restrictions>> response = restrictionsController.getAllRestrictions();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllRestrictions_RestrictionsFound() {
        Restrictions restriction = new Restrictions();
        when(restrictRepo.findAll()).thenReturn(List.of(restriction));

        ResponseEntity<List<Restrictions>> response = restrictionsController.getAllRestrictions();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetUsersByName_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodClass>> response = restrictionsController.getUsersByName(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetUsersByName_UserFound() {
        UserData user = new UserData();
        FoodClass foodClass = new FoodClass();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(restrictRepo.findDistinctClasses(1)).thenReturn(List.of(foodClass));
        when(foodClassRepo.findAll()).thenReturn(new ArrayList<>(List.of(foodClass)));

        ResponseEntity<List<FoodClass>> response = restrictionsController.getUsersByName(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddRestriction_RestrictionAlreadyExists() {
        Restrictions restriction = new Restrictions();
        restriction.setrestrictedName("ExistingRestriction");

        when(restrictRepo.findByRestrictedName("ExistingRestriction")).thenReturn(Optional.of(restriction));

        ResponseEntity<Restrictions> response = restrictionsController.addRestriction(restriction);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testAddRestriction_NewRestriction() {
        Restrictions restriction = new Restrictions();
        restriction.setrestrictedName("NewRestriction");

        when(restrictRepo.findByRestrictedName("NewRestriction")).thenReturn(Optional.empty());
        when(foodGroupRepo.findById(1)).thenReturn(Optional.of(new FoodGroup()));
        when(foodClassRepo.findById(1)).thenReturn(Optional.of(new FoodClass()));
        when(foodTypeRepo.findById(1)).thenReturn(Optional.of(new FoodType()));
        when(ingredienRepo.findById(1)).thenReturn(Optional.of(new Ingredients()));

        ResponseEntity<Restrictions> response = restrictionsController.addRestriction(restriction);

        assertEquals(201, response.getStatusCodeValue());
    }
}
