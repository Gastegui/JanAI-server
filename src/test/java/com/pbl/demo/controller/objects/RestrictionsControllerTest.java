package com.pbl.demo.controller.objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @InjectMocks
    private RestrictionsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestrictions_Success() {
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
    void testGetAllRestrictions_NotFound() {
        // Arrange
        when(restrictRepo.findAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByName_UserNotFound() {
        // Arrange
        int userId = 1;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByName_Success() {
        // Arrange
        int userId = 1;
        UserData user = new UserData();
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        List<FoodClass> foodClasses = List.of(new FoodClass());
        List<FoodClass> foodClassList = List.of(new FoodClass());

        when(restrictRepo.findDistinctClasses(userId)).thenReturn(foodClasses);
        when(foodClassRepo.findAll()).thenReturn(foodClassList);

        // Act
        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetUsersByType_UserNotFound() {
        // Arrange
        int userId = 1;
        String className = "ClassName";
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userId, className);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByGroup_UserNotFound() {
        // Arrange
        int userId = 1;
        String typeName = "TypeName";
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userId, typeName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByIngredients_UserNotFound() {
        // Arrange
        int userId = 1;
        String groupName = "GroupName";
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userId, groupName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddRestriction_UserNotFound() {
        // Arrange
        int userId = 1;
        Restrictions restriction = new Restrictions();
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Restrictions> response = controller.addRestriction(userId, restriction);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddRestriction_RestrictionAlreadyExists() {
        // Arrange
        int userId = 1;
        Restrictions restriction = new Restrictions();
        UserData user = new UserData();
        restriction.setRestrictedName("RestrictionName");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(restrictRepo.findByRestrictedName(restriction.getRestrictedName())).thenReturn(Optional.of(restriction));

        // Act
        ResponseEntity<Restrictions> response = controller.addRestriction(userId, restriction);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddRestriction_Success() {
        // Arrange
        int userId = 1;
        Restrictions restriction = new Restrictions();
        UserData user = new UserData();
        restriction.setRestrictedName("RestrictionName");
        restriction.setFoodClass(new FoodClass());
        restriction.setFoodGroup(new FoodGroup());
        restriction.setFoodType(new FoodType());
        restriction.setIngredients(new Ingredients());

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(restrictRepo.findByRestrictedName(restriction.getRestrictedName())).thenReturn(Optional.empty());
        when(foodGroupRepo.findById(anyInt())).thenReturn(Optional.of(new FoodGroup()));
        when(foodClassRepo.findById(anyInt())).thenReturn(Optional.of(new FoodClass()));
        when(foodTypeRepo.findById(anyInt())).thenReturn(Optional.of(new FoodType()));
        when(ingredienRepo.findById(anyInt())).thenReturn(Optional.of(new Ingredients()));

        // Act
        ResponseEntity<Restrictions> response = controller.addRestriction(userId, restriction);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
