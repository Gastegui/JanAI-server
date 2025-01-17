package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.*;

class RestrictionsControllerTest {

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

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RestrictionsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestrictions_NoRestrictionsFound() {
        // Arrange
        when(restrictRepo.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllRestrictions_RestrictionsFound() {
        // Arrange
        List<Restrictions> restrictions = List.of(new Restrictions());
        when(restrictRepo.findAll()).thenReturn(restrictions);

        // Act
        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restrictions, response.getBody());
    }

    @Test
    void testGetUsersByName_UserNotFound() {
        // Arrange
        int userID = 1;
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(userID);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByName_Success() {
        // Arrange
        int userID = 1;
        UserData user = new UserData();
        user.setUserID(userID);
        List<FoodClass> foodClasses = List.of(new FoodClass());
        List<FoodClass> foodClassList = List.of(new FoodClass());
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(restrictRepo.findDistinctClasses(userID)).thenReturn(foodClasses);
        when(foodClassRepo.findAll()).thenReturn(foodClassList);

        // Act
        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(userID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodClassList, response.getBody());
    }

    @Test
    void testGetUsersByType_UserOrClassNotFound() {
        // Arrange
        int userID = 1;
        String className = "Class";
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByType_Success() {
        // Arrange
        int userID = 1;
        String className = "Class";
        FoodClass foodClass = new FoodClass();
        foodClass.setClassID(1);
        List<FoodType> foodTypes = List.of(new FoodType());
        when(userRepo.findById(userID)).thenReturn(Optional.of(new UserData()));
        when(foodClassRepo.findByClassName(className)).thenReturn(Optional.of(foodClass));
        when(foodTypeRepo.findByClassID(foodClass.getClassID())).thenReturn(foodTypes);
        when(restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID(), userID)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodTypes, response.getBody());
    }

    @Test
    void testGetUsersByGroup_UserOrTypeNotFound() {
        // Arrange
        int userID = 1;
        String typeName = "Type";
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userID, typeName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByGroup_Success() {
        // Arrange
        int userID = 1;
        String typeName = "Type";
        FoodType foodType = new FoodType();
        foodType.setTypeId(1);
        List<FoodGroup> foodGroups = List.of(new FoodGroup());
        when(userRepo.findById(userID)).thenReturn(Optional.of(new UserData()));
        when(foodTypeRepo.findByTypeName(typeName)).thenReturn(Optional.of(foodType));
        when(foodGroupRepo.findByTypeID(foodType.getTypeId())).thenReturn(foodGroups);
        when(restrictRepo.findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userID, typeName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodGroups, response.getBody());
    }

    @Test
    void testGetUsersByIngredients_UserOrGroupNotFound() {
        // Arrange
        int userID = 1;
        String groupName = "Group";
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsersByIngredients_Success() {
        // Arrange
        int userID = 1;
        String groupName = "Group";
        FoodGroup foodGroup = new FoodGroup();
        foodGroup.setGroupID(1);
        List<Ingredients> ingredients = List.of(new Ingredients());
        when(userRepo.findById(userID)).thenReturn(Optional.of(new UserData()));
        when(foodGroupRepo.findByGroupName(groupName)).thenReturn(Optional.of(foodGroup));
        when(ingredienRepo.findByGroupID(foodGroup.getGroupID())).thenReturn(ingredients);
        when(restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.getGroupID(), userID)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredients, response.getBody());
    }

    @Test
    void testAddRestriction_InvalidRequestBody() {
        // Arrange
        int userID = 1;
        Restrictions restriction = new Restrictions();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        // Act
        ResponseEntity<Restrictions> response = controller.addRestriction(userID, restriction, result);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAddRestriction_UserNotFound() {
        // Arrange
        int userID = 1;
        Restrictions restriction = new Restrictions();
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Restrictions> response = controller.addRestriction(userID, restriction, mock(BindingResult.class));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddRestriction_FoodComponentsNotFound() {
        // Arrange
        int userID = 1;
        Restrictions restriction = new Restrictions();
        when(userRepo.findById(userID)).thenReturn(Optional.of(new UserData()));
        when(foodClassRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(foodTypeRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(foodGroupRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(ingredienRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Restrictions> response = controller.addRestriction(userID, restriction, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}