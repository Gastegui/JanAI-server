package com.pbl.demo.controller.objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.food_class.*;
import com.pbl.demo.model.food_group.*;
import com.pbl.demo.model.food_type.*;
import com.pbl.demo.model.ingredients.*;
import com.pbl.demo.model.restrictions.*;
import com.pbl.demo.model.user_data.*;

class RestrictionsControllerTest {

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
    void testGetAllRestrictions_EmptyList() {
        when(restrictRepo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Restrictions>> response = restrictionsController.getAllRestrictions();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllRestrictions_NonEmptyList() {
        Restrictions restriction = new Restrictions();
        List<Restrictions> restrictions = List.of(restriction);
        when(restrictRepo.findAll()).thenReturn(restrictions);

        ResponseEntity<List<Restrictions>> response = restrictionsController.getAllRestrictions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restrictions, response.getBody());
    }

    @Test
    void testAddRestriction_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Restrictions restriction = new Restrictions();
        ResponseEntity<Restrictions> response = restrictionsController.addRestriction(1, restriction, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddRestriction_InvalidReferences() {
        UserData user = new UserData();
        FoodClass fClass = new FoodClass();
        FoodGroup fGroup = new FoodGroup();
        FoodType fType = new FoodType();
        Ingredients ing = new Ingredients();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodClassRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(foodGroupRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(foodTypeRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(ingredienRepo.findById(anyInt())).thenReturn(Optional.empty());

        Restrictions restriction = new Restrictions();
        restriction.setFoodClass(fClass);
        restriction.setFoodGroup(fGroup);
        restriction.setFoodType(fType);
        restriction.setIngredients(ing);
        ResponseEntity<Restrictions> response = restrictionsController.addRestriction(1, restriction, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddRestriction_Valid() {
        UserData user = new UserData();
        FoodClass fClass = new FoodClass();
        FoodGroup fGroup = new FoodGroup();
        FoodType fType = new FoodType();
        Ingredients ing = new Ingredients();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodClassRepo.findById(anyInt())).thenReturn(Optional.of(fClass));
        when(foodGroupRepo.findById(anyInt())).thenReturn(Optional.of(fGroup));
        when(foodTypeRepo.findById(anyInt())).thenReturn(Optional.of(fType));
        when(ingredienRepo.findById(anyInt())).thenReturn(Optional.of(ing));

        Restrictions restriction = new Restrictions();
        restriction.setFoodClass(fClass);
        restriction.setFoodGroup(fGroup);
        restriction.setFoodType(fType);
        restriction.setIngredients(ing);
        ResponseEntity<Restrictions> response = restrictionsController.addRestriction(1, restriction, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(restrictRepo, times(1)).save(restriction);
    }

    @Test
    void testGetRestrictionsByClass_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodClass>> response = restrictionsController.getUsersByName(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetRestrictionsByClass_Valid() {
        UserData user = new UserData();
        FoodClass foodClass = new FoodClass();
        List<FoodClass> allFoodClasses = List.of(foodClass);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(restrictRepo.findDistinctClasses(1)).thenReturn(Collections.emptyList());
        when(foodClassRepo.findAll()).thenReturn(allFoodClasses);

        ResponseEntity<List<FoodClass>> response = restrictionsController.getUsersByName(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allFoodClasses, response.getBody());
    }

    @Test
    void testGetRestrictionsByType_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodType>> response = restrictionsController.getUsersByType(1, "ClassName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetRestrictionsByType_ClassNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.of(new UserData()));
        when(foodClassRepo.findByClassName("ClassName")).thenReturn(Optional.empty());

        ResponseEntity<List<FoodType>> response = restrictionsController.getUsersByType(1, "ClassName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetRestrictionsByType_Valid() {
        UserData user = new UserData();
        FoodClass foodClass = new FoodClass();
        foodClass.setClassID(1);
        FoodType foodType = new FoodType();
        List<FoodType> allFoodTypes = List.of(foodType);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodClassRepo.findByClassName("ClassName")).thenReturn(Optional.of(foodClass));
        when(foodTypeRepo.findByClassID(1)).thenReturn(allFoodTypes);
        when(restrictRepo.findDistinctTypeIDsByClassID(1, 1)).thenReturn(Collections.emptyList());

        ResponseEntity<List<FoodType>> response = restrictionsController.getUsersByType(1, "ClassName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allFoodTypes, response.getBody());
    }

    @Test
    void testGetRestrictionsByGroup_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodGroup>> response = restrictionsController.getUsersByGroup(1, "TypeName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetRestrictionsByGroup_TypeNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.of(new UserData()));
        when(foodTypeRepo.findByTypeName("TypeName")).thenReturn(Optional.empty());

        ResponseEntity<List<FoodGroup>> response = restrictionsController.getUsersByGroup(1, "TypeName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetRestrictionsByGroup_Valid() {
        UserData user = new UserData();
        FoodType foodType = new FoodType();
        foodType.setTypeId(1);
        FoodGroup foodGroup = new FoodGroup();
        List<FoodGroup> allFoodGroups = List.of(foodGroup);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodTypeRepo.findByTypeName("TypeName")).thenReturn(Optional.of(foodType));
        when(foodGroupRepo.findByTypeID(1)).thenReturn(allFoodGroups);
        when(restrictRepo.findDistinctGruopIDsByTypeID(1, 1)).thenReturn(Collections.emptyList());

        ResponseEntity<List<FoodGroup>> response = restrictionsController.getUsersByGroup(1, "TypeName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allFoodGroups, response.getBody());
    }
}
