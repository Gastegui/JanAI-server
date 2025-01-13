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

import com.pbl.demo.model.food_class.FoodClass;
import com.pbl.demo.model.food_class.FoodClassRepository;
import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.food_group.FoodGroupRepository;
import com.pbl.demo.model.food_type.FoodType;
import com.pbl.demo.model.food_type.FoodTypeRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.restrictions.RestrictionsRepository;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

class RestrictionsControllerTest {

    @InjectMocks
    private RestrictionsController controller;

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
    void testGetAllRestrictions_Success() {
        List<Restrictions> restrictions = List.of(new Restrictions(), new Restrictions());
        when(restrictRepo.findAll()).thenReturn(restrictions);

        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restrictions, response.getBody());
        verify(restrictRepo).findAll();
    }

    @Test
    void testGetAllRestrictions_NotFound() {
        when(restrictRepo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(restrictRepo).findAll();
    }

    @Test
void testGetUsersByName_Success() {
    int userID = 1;
    UserData user = new UserData();
    FoodClass foodClass = new FoodClass();
    foodClass.setClassID(1);
    FoodType foodType = new FoodType();
    foodType.setTypeId(1);

    when(userRepo.findById(userID)).thenReturn(Optional.of(user));
    when(restrictRepo.findDistinctClasses(userID)).thenReturn(List.of(foodClass));
    when(foodClassRepo.findAll()).thenReturn(new ArrayList<>(List.of(foodClass))); // Mutable list
    when(restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID(), userID)).thenReturn(List.of());
    when(foodTypeRepo.findByClassID(foodClass.getClassID())).thenReturn(List.of(foodType));

    ResponseEntity<List<FoodClass>> response = controller.getUsersByName(userID);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    verify(userRepo).findById(userID);
    verify(restrictRepo).findDistinctClasses(userID);
    verify(foodClassRepo).findAll();
}


    @Test
    void testGetUsersByName_UserNotFound() {
        int userID = 1;
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(userID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepo).findById(userID);
        verifyNoInteractions(restrictRepo);
    }

    @Test
    void testGetUsersByType_Success() {
        int userID = 1;
        String className = "Test Class";
        UserData user = new UserData();
        FoodClass foodClass = new FoodClass();
        foodClass.setClassID(1);
        FoodType foodType = new FoodType();
        foodType.setTypeId(1);

        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodClassRepo.findByClassName(className)).thenReturn(Optional.of(foodClass));
        when(foodTypeRepo.findByClassID(foodClass.getClassID())).thenReturn(List.of(foodType));
        when(restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID(), userID)).thenReturn(Collections.emptyList());

        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userRepo).findById(userID);
        verify(foodClassRepo).findByClassName(className);
        verify(foodTypeRepo).findByClassID(foodClass.getClassID());
        verify(restrictRepo).findDistinctTypeIDsByClassID(foodClass.getClassID(), userID);
    }

    @Test
    void testGetUsersByType_UserNotFound() {
        int userID = 1;
        String className = "Test Class";
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepo).findById(userID);
        verifyNoInteractions(foodClassRepo);
    }

    @Test
    void testGetUsersByType_FoodClassNotFound() {
        UserData user = new UserData();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodClassRepo.findByClassName("ClassName")).thenReturn(Optional.empty());

        ResponseEntity<List<FoodType>> response = controller.getUsersByType(1, "ClassName");

        assertEquals(HttpStatus .NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByType_Valid() {
        UserData user = new UserData();
        FoodClass foodClass = new FoodClass();
        foodClass.setClassID(1);
        FoodType foodType = new FoodType();
        List<FoodType> allFoodTypes = Arrays.asList(foodType);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodClassRepo.findByClassName("ClassName")).thenReturn(Optional.of(foodClass));
        when(foodTypeRepo.findByClassID(1)).thenReturn(allFoodTypes);
        when(restrictRepo.findDistinctTypeIDsByClassID(1, 1)).thenReturn(Collections.emptyList());

        ResponseEntity<List<FoodType>> response = controller.getUsersByType(1, "ClassName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allFoodTypes, response.getBody());
    }

    @Test
    void testGetUsersByGroup_Success() {
        int userID = 1;
        String typeName = "Test Type";
        UserData user = new UserData();
        FoodType foodType = new FoodType();
        foodType.setTypeId(1);
        FoodGroup foodGroup = new FoodGroup();

        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodTypeRepo.findByTypeName(typeName)).thenReturn(Optional.of(foodType));
        when(foodGroupRepo.findByTypeID(foodType.getTypeId())).thenReturn(List.of(foodGroup));
        when(restrictRepo.findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID)).thenReturn(Collections.emptyList());

        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userID, typeName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userRepo).findById(userID);
        verify(foodTypeRepo).findByTypeName(typeName);
        verify(foodGroupRepo).findByTypeID(foodType.getTypeId());
        verify(restrictRepo).findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID);
    }

    @Test
    void testGetUsersByGroup_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(1, "TypeName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByGroup_FoodTypeNotFound() {
        UserData user = new UserData();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodTypeRepo.findByTypeName("TypeName")).thenReturn(Optional.empty());

        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(1, "TypeName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByIngredients_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(1, "GroupName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByIngredients_GroupNotFound() {
        UserData user = new UserData();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodGroupRepo.findByGroupName("GroupName")).thenReturn(Optional.empty());

        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(1, "GroupName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByIngredients_Valid() {
        UserData user = new UserData();
        FoodGroup foodGroup = new FoodGroup();
        foodGroup.setGroupID(1);
        Ingredients ingredient = new Ingredients();
        List<Ingredients> allIngredients = Arrays.asList(ingredient);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(foodGroupRepo.findByGroupName("GroupName")).thenReturn(Optional.of(foodGroup));
        when(ingredienRepo.findByGroupID(1)).thenReturn(allIngredients);
        when(restrictRepo.findDistinctIngredientIDsByGroupID(1, 1)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(1, "GroupName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allIngredients, response.getBody());
    }


    @Test
    void testAddRestriction_Success() {
        int userID = 1;
        UserData user = new UserData();
        Restrictions restriction = new Restrictions();
        FoodGroup group = new FoodGroup();
        FoodClass foodClass = new FoodClass();
        FoodType type = new FoodType();
        Ingredients ingredient = new Ingredients();

        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(restrictRepo.findByRestrictedName(restriction.getRestrictedName())).thenReturn(Optional.empty());
        when(foodGroupRepo.findById(group.getGroupID())).thenReturn(Optional.of(group));
        when(foodClassRepo.findById(foodClass.getClassID())).thenReturn(Optional.of(foodClass));
        when(foodTypeRepo.findById(type.getTypeId())).thenReturn(Optional.of(type));
        when(ingredienRepo.findById(ingredient.getIngredientID())).thenReturn(Optional.of(ingredient));

        restriction.setFoodGroup(group);
        restriction.setFoodClass(foodClass);
        restriction.setFoodType(type);
        restriction.setIngredients(ingredient);

        ResponseEntity<Restrictions> response = controller.addRestriction(userID, restriction);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restriction, response.getBody());
        verify(restrictRepo).save(restriction);
    }

    @Test
    void testAddRestriction_UserNotFound() {
        int userID = 1;
        Restrictions restriction = new Restrictions();
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        ResponseEntity<Restrictions> response = controller.addRestriction(userID, restriction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepo).findById(userID);
        verifyNoInteractions(restrictRepo);
    }

    @Test
    void testAddRestriction_DuplicateRestriction() {
        UserData user = new UserData();
        Restrictions restriction = new Restrictions();
        restriction.setRestrictedName("Test");

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(restrictRepo.findByRestrictedName("Test")).thenReturn(Optional.of(restriction));

        ResponseEntity<Restrictions> response = controller.addRestriction(1, restriction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
