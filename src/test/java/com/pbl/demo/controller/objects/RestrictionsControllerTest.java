package com.pbl.demo.controller.objects;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    private BindingResult result;

    @InjectMocks
    private RestrictionsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestrictions_NoRestrictions() {
        when(restrictRepo.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllRestrictions_WithRestrictions() {
        List<Restrictions> restrictions = List.of(new Restrictions(), new Restrictions());
        when(restrictRepo.findAll()).thenReturn(restrictions);

        ResponseEntity<List<Restrictions>> response = controller.getAllRestrictions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restrictions, response.getBody());
    }

    @Test
    void testGetUsersByName_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByName_ValidUser() {
        UserData user = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(restrictRepo.findDistinctClasses(1)).thenReturn(new ArrayList<>());
        when(foodClassRepo.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<FoodClass>> response = controller.getUsersByName(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddRestriction_ValidationError() {
        Restrictions restriction = new Restrictions();

        when(result.hasErrors()).thenReturn(true);

        ResponseEntity<Restrictions> response = controller.addRestriction(1, restriction, result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Validation-Error"));
    }

    @Test
    void testAddRestriction_UserNotFound() {
        Restrictions restriction = new Restrictions();

        when(result.hasErrors()).thenReturn(false);
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Restrictions> response = controller.addRestriction(1, restriction, result);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddRestriction_MissingComponents() {
        Restrictions restriction = new Restrictions();
        restriction.setFoodGroup(null);
        restriction.setFoodClass(null);
        restriction.setFoodType(null);
        restriction.setIngredients(null);


        when(result.hasErrors()).thenReturn(false);
        when(userRepo.findById(1)).thenReturn(Optional.of(new UserData()));

        ResponseEntity<Restrictions> response = controller.addRestriction(1, restriction, result);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddRestriction_ValidRestriction() {
        Restrictions restriction = new Restrictions();
        restriction.setFoodGroup(new FoodGroup());
        restriction.setFoodClass(new FoodClass());
        restriction.setFoodType(new FoodType());
        restriction.setIngredients(new Ingredients());


        when(result.hasErrors()).thenReturn(false);
        when(userRepo.findById(1)).thenReturn(Optional.of(new UserData()));
        when(foodGroupRepo.findById(anyInt())).thenReturn(Optional.of(new FoodGroup()));
        when(foodClassRepo.findById(anyInt())).thenReturn(Optional.of(new FoodClass()));
        when(foodTypeRepo.findById(anyInt())).thenReturn(Optional.of(new FoodType()));
        when(ingredienRepo.findById(anyInt())).thenReturn(Optional.of(new Ingredients()));

        ResponseEntity<Restrictions> response = controller.addRestriction(1, restriction, result);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(restrictRepo, times(1)).save(restriction);
    }

    @Test
    void testFilterInvalidFoodClasses_RemovesInvalidClasses() {
        int userID = 1;

        // Mock data
        FoodClass validClass = new FoodClass(1, "ValidClass");
        FoodClass invalidClass = new FoodClass(2, "InvalidClass");
        List<FoodClass> foodClasses = Arrays.asList(validClass, invalidClass);
        List<FoodClass> foodClassList = new ArrayList<>(Arrays.asList(validClass, invalidClass));

        List<FoodType> restrictedTypesForValidClass = Collections.singletonList(new FoodType(1, "Type 1", invalidClass));
        List<FoodType> allTypesForValidClass = Arrays.asList(
                new FoodType(1, "Type1", validClass),
                new FoodType(2, "Type2", invalidClass)
        );

        // Mocking repo calls
        when(restrictRepo.findDistinctTypeIDsByClassID(1, userID)).thenReturn(restrictedTypesForValidClass);
        when(foodTypeRepo.findByClassID(1)).thenReturn(allTypesForValidClass);
        when(restrictRepo.findDistinctTypeIDsByClassID(2, userID)).thenReturn(Collections.emptyList());
        when(foodTypeRepo.findByClassID(2)).thenReturn(Collections.emptyList());

        // Execute
        controller.filterInvalidFoodClasses(userID, foodClasses, foodClassList);

        // Verify
        assertEquals(1, foodClassList.size());
        assertTrue(foodClassList.contains(validClass));
        assertFalse(foodClassList.contains(invalidClass));
    }

    @Test
    void testFilterInvalidFoodClasses_EmptyLists_NoChange() {
        int userID = 1;

        // Empty input lists
        List<FoodClass> foodClasses = Collections.emptyList();
        List<FoodClass> foodClassList = new ArrayList<>();

        // Execute
        controller.filterInvalidFoodClasses(userID, foodClasses, foodClassList);

        // Verify
        assertTrue(foodClassList.isEmpty());
    }

    @Test
    void testFilterInvalidFoodClasses_ClassNotInList_NoChange() {
        int userID = 1;

        // Mock data
        FoodClass validClass = new FoodClass(1, "ValidClass");
        FoodClass invalidClass = new FoodClass(2, "InvalidClass");
        List<FoodClass> foodClasses = Arrays.asList(invalidClass);
        List<FoodClass> foodClassList = new ArrayList<>(Collections.singletonList(validClass)); // Only validClass is in the list

        List<FoodType> restrictedTypesForValidClass = Collections.emptyList();
        List<FoodType> allTypesForValidClass = Collections.emptyList();

        // Mocking repo calls
        when(restrictRepo.findDistinctTypeIDsByClassID(1, userID)).thenReturn(restrictedTypesForValidClass);
        when(foodTypeRepo.findByClassID(1)).thenReturn(allTypesForValidClass);

        // Execute
        controller.filterInvalidFoodClasses(userID, foodClasses, foodClassList);

        // Verify
        assertEquals(1, foodClassList.size());
        assertTrue(foodClassList.contains(validClass));
    }

    @Test
    void testShouldRemoveFoodClass_AllTypesRestricted_ReturnsTrue() {
        FoodClass invaliFoodClass = new FoodClass();
        
        // Mock data
        List<FoodType> restrictedTypes = Arrays.asList(
                new FoodType(1, "Type1", invaliFoodClass),
                new FoodType(2, "Type2", invaliFoodClass)
        );
        List<FoodType> allTypes = Arrays.asList(
                new FoodType(1, "Type1", invaliFoodClass),
                new FoodType(2, "Type2", invaliFoodClass)
        );

        // Execute and Verify
        assertTrue(controller.shouldRemoveFoodClass(restrictedTypes, allTypes));
    }

    @Test
    void testShouldRemoveFoodClass_TypeWithZeroID_ReturnsTrue() {
        FoodClass invalidClass = new FoodClass();
        FoodClass validClass = new FoodClass();
        
        // Mock data
        List<FoodType> restrictedTypes = Collections.singletonList(new FoodType(0, "Type0", invalidClass));
        List<FoodType> allTypes = Arrays.asList(
                new FoodType(1, "Type1", invalidClass),
                new FoodType(2, "Type2", validClass)
        );

        // Execute and Verify
        assertTrue(controller.shouldRemoveFoodClass(restrictedTypes, allTypes));
    }

    @Test
    void testShouldRemoveFoodClass_NotAllTypesRestricted_ReturnsFalse() {
        FoodClass validClass = new FoodClass();
        
        // Mock data
        List<FoodType> restrictedTypes = Collections.singletonList(new FoodType(1, "Type1", validClass));
        List<FoodType> allTypes = Arrays.asList(
                new FoodType(1, "Type1", validClass),
                new FoodType(2, "Type2", validClass)
        );

        // Execute and Verify
        assertFalse(controller.shouldRemoveFoodClass(restrictedTypes, allTypes));
    }

    @Test
    void testShouldRemoveFoodClass_EmptyRestrictedTypes_ReturnsFalse() {
        FoodClass validClass = new FoodClass();
        
        // Mock data
        List<FoodType> restrictedTypes = Collections.emptyList();
        List<FoodType> allTypes = Arrays.asList(
                new FoodType(1, "Type1", validClass),
                new FoodType(2, "Type2", validClass)
        );

        // Execute and Verify
        assertFalse(controller.shouldRemoveFoodClass(restrictedTypes, allTypes));
    }

    @Test
    void testGetUsersByType_UserNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;
        String className = "TestClass";

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByType_ClassNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;
        String className = "TestClass";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername("username");

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodClassRepo.findByClassName(className)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByType_ReturnsFilteredFoodTypes() {
        // Mock data
        int userID = 1;
        String className = "TestClass";
        FoodClass foodClass = new FoodClass(1, className);
        List<FoodType> allFoodTypes = new ArrayList<>(Arrays.asList(
                new FoodType(1, "Type1", foodClass),
                new FoodType(2, "Type2", foodClass)
        ));
        List<FoodType> restrictedFoodTypes = Collections.singletonList(new FoodType(1, "Type1", foodClass));
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername("username");


        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodClassRepo.findByClassName(className)).thenReturn(Optional.of(foodClass));
        when(foodTypeRepo.findByClassID(foodClass.getClassID())).thenReturn(allFoodTypes);
        when(restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID(), userID)).thenReturn(restrictedFoodTypes);
        when(foodGroupRepo.findByTypeID(1)).thenReturn(Collections.emptyList());
        when(restrictRepo.findDistinctGruopIDsByTypeID(1, userID)).thenReturn(Collections.emptyList());

        // Execute
        ResponseEntity<List<FoodType>> response = controller.getUsersByType(userID, className);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testFilterInvalidFoodTypes_RemovesInvalidTypes() {
        int userID = 1;
        FoodClass fClass = new FoodClass();
        // Mock data
        FoodType validType = new FoodType(1, "Type1", fClass);
        FoodType invalidType = new FoodType(2, "Type2", fClass);
        List<FoodType> foodTypeList = new ArrayList<>(Arrays.asList(validType, invalidType));
        List<FoodType> restrictedFoodTypes = Collections.singletonList(invalidType);

        List<FoodGroup> restrictedGroupsForInvalidType = Collections.singletonList(new FoodGroup(1, "Group1", invalidType));
        List<FoodGroup> allGroupsForInvalidType = Collections.singletonList(new FoodGroup(1, "Group1", invalidType));

        // Mocking repo calls
        when(restrictRepo.findDistinctGruopIDsByTypeID(invalidType.getTypeId(), userID))
                .thenReturn(restrictedGroupsForInvalidType);
        when(foodGroupRepo.findByTypeID(invalidType.getTypeId())).thenReturn(allGroupsForInvalidType);

        // Execute
        controller.filterInvalidFoodTypes(userID, restrictedFoodTypes, foodTypeList);

        // Verify
        assertEquals(1, foodTypeList.size());
        assertTrue(foodTypeList.contains(validType));
        assertFalse(foodTypeList.contains(invalidType));
    }

    @Test
    void testShouldRemoveFoodType_AllGroupsRestricted_ReturnsTrue() {
        FoodType fType = new FoodType();
        
        // Mock data
        List<FoodGroup> restrictedGroups = Arrays.asList(
                new FoodGroup(1, "Group1", fType),
                new FoodGroup(2, "Group2", fType)
        );
        List<FoodGroup> allGroups = Arrays.asList(
                new FoodGroup(1, "Group1", fType),
                new FoodGroup(2, "Group2", fType)
        );

        // Execute and Verify
        assertTrue(controller.shouldRemoveFoodType(restrictedGroups, allGroups));
    }

    @Test
    void testShouldRemoveFoodType_GroupWithZeroID_ReturnsTrue() {
        FoodType fType = new FoodType();
        // Mock data
        List<FoodGroup> restrictedGroups = Collections.singletonList(new FoodGroup(0, "Group0", fType));
        List<FoodGroup> allGroups = Arrays.asList(
                new FoodGroup(1, "Group1", fType),
                new FoodGroup(2, "Group2", fType)
        );

        // Execute and Verify
        assertTrue(controller.shouldRemoveFoodType(restrictedGroups, allGroups));
    }

    @Test
    void testShouldRemoveFoodType_NotAllGroupsRestricted_ReturnsFalse() {
        FoodType fType = new FoodType();
        
        // Mock data
        List<FoodGroup> restrictedGroups = Collections.singletonList(new FoodGroup(1, "Group1", fType));
        List<FoodGroup> allGroups = Arrays.asList(
                new FoodGroup(1, "Group1", fType),
                new FoodGroup(2, "Group2", fType)
        );

        // Execute and Verify
        assertFalse(controller.shouldRemoveFoodType(restrictedGroups, allGroups));
    }

    @Test
    void testShouldRemoveFoodType_EmptyRestrictedGroups_ReturnsFalse() {
        FoodType fType = new FoodType();
        
        // Mock data
        List<FoodGroup> restrictedGroups = Collections.emptyList();
        List<FoodGroup> allGroups = Arrays.asList(
                new FoodGroup(1, "Group1", fType),
                new FoodGroup(2, "Group2", fType)
        );

        // Execute and Verify
        assertFalse(controller.shouldRemoveFoodType(restrictedGroups, allGroups));
    }

    @Test
    void testGetUsersByGroup_UserNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;
        String typeName = "TestType";

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userID, typeName);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByGroup_TypeNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;
        String typeName = "TestType";
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);


        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodTypeRepo.findByTypeName(typeName)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userID, typeName);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByGroup_ReturnsFilteredFoodGroups() {
        // Mock data
        int userID = 1;
        String typeName = "TestType";
        FoodClass fClass = new FoodClass(); 
        FoodType foodType = new FoodType(1, typeName, fClass);
        List<FoodGroup> allFoodGroups = new ArrayList<>(Arrays.asList(
                new FoodGroup(1, "Group1", foodType),
                new FoodGroup(2, "Group2", foodType)
        ));
        List<FoodGroup> restrictedFoodGroups = Collections.singletonList(new FoodGroup(1, "Group1", foodType));
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodTypeRepo.findByTypeName(typeName)).thenReturn(Optional.of(foodType));
        when(foodGroupRepo.findByTypeID(foodType.getTypeId())).thenReturn(allFoodGroups);
        when(restrictRepo.findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID)).thenReturn(restrictedFoodGroups);
        when(ingredienRepo.findByGroupID(1)).thenReturn(Collections.emptyList());
        when(restrictRepo.findDistinctIngredientIDsByGroupID(1, userID)).thenReturn(Collections.emptyList());

        // Execute
        ResponseEntity<List<FoodGroup>> response = controller.getUsersByGroup(userID, typeName);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testFilterInvalidFoodGroups_RemovesInvalidGroups() {
        int userID = 1;
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);
        FoodType fType = new FoodType();
        // Mock data
        FoodGroup validGroup = new FoodGroup(1, "Group1", fType);
        FoodGroup invalidGroup = new FoodGroup(2, "Group2", fType);
        List<FoodGroup> foodGroupList = new ArrayList<>(Arrays.asList(validGroup, invalidGroup));
        List<FoodGroup> restrictedFoodGroups = Collections.singletonList(invalidGroup);

        List<Ingredients> restrictedIngredientsForInvalidGroup = Collections.singletonList(new Ingredients(1, "Ingredient1", invalidGroup));
        List<Ingredients> allIngredientsForInvalidGroup = Collections.singletonList(new Ingredients(1, "Ingredient1", invalidGroup));

        
        // Mocking repo calls
        when(restrictRepo.findDistinctIngredientIDsByGroupID(invalidGroup.getGroupID(), userID))
                .thenReturn(restrictedIngredientsForInvalidGroup);
        when(ingredienRepo.findByGroupID(invalidGroup.getGroupID())).thenReturn(allIngredientsForInvalidGroup);

        // Execute
        controller.filterInvalidFoodGroups(userID, restrictedFoodGroups, foodGroupList);

        // Verify
        assertEquals(1, foodGroupList.size());
        assertTrue(foodGroupList.contains(validGroup));
        assertFalse(foodGroupList.contains(invalidGroup));
    }

    @Test
    void testShouldRemoveFoodGroup_AllIngredientsRestricted_ReturnsTrue() {
        FoodGroup fGroup = new FoodGroup();
        
        // Mock data
        List<Ingredients> restrictedIngredients = Arrays.asList(
                new Ingredients(1, "Ingredient1", fGroup),
                new Ingredients(2, "Ingredient2", fGroup)
        );
        List<Ingredients> allIngredients = Arrays.asList(
                new Ingredients(1, "Ingredient1", fGroup),
                new Ingredients(2, "Ingredient2", fGroup)
        );

        // Execute and Verify
        assertTrue(controller.shouldRemoveFoodGroup(restrictedIngredients, allIngredients));
    }

    @Test
    void testShouldRemoveFoodGroup_IngredientWithZeroID_ReturnsTrue() {
        FoodGroup fGroup = new FoodGroup();

        // Mock data
        List<Ingredients> restrictedIngredients = Collections.singletonList(new Ingredients(0, "Ingredient0", fGroup));
        List<Ingredients> allIngredients = Arrays.asList(
                new Ingredients(1, "Ingredient1", fGroup),
                new Ingredients(2, "Ingredient2", fGroup)
        );

        // Execute and Verify
        assertTrue(controller.shouldRemoveFoodGroup(restrictedIngredients, allIngredients));
    }

    @Test
    void testShouldRemoveFoodGroup_NotAllIngredientsRestricted_ReturnsFalse() {
        FoodGroup fGroup = new FoodGroup();

        // Mock data
        List<Ingredients> restrictedIngredients = Collections.singletonList(new Ingredients(1, "Ingredient1", fGroup));
        List<Ingredients> allIngredients = Arrays.asList(
                new Ingredients(1, "Ingredient1", fGroup),
                new Ingredients(2, "Ingredient2", fGroup)
        );

        // Execute and Verify
        assertFalse(controller.shouldRemoveFoodGroup(restrictedIngredients, allIngredients));
    }

    @Test
    void testShouldRemoveFoodGroup_EmptyRestrictedIngredients_ReturnsFalse() {
        FoodGroup fGroup = new FoodGroup();

        
        // Mock data
        List<Ingredients> restrictedIngredients = Collections.emptyList();
        List<Ingredients> allIngredients = Arrays.asList(
                new Ingredients(1, "Ingredient1", fGroup),
                new Ingredients(2, "Ingredient2", fGroup)
        );

        // Execute and Verify
        assertFalse(controller.shouldRemoveFoodGroup(restrictedIngredients, allIngredients));
    }

    @Test
    void testGetUsersByIngredients_UserNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;
        String groupName = "TestGroup";

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByIngredients_GroupNotFound_ReturnsNotFound() {
        // Mock data
        int userID = 1;
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);
        String groupName = "TestGroup";

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodGroupRepo.findByGroupName(groupName)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsersByIngredients_ReturnsFilteredIngredients() {
        // Mock data
        FoodType fType = new FoodType();
        int userID = 1;
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);
        String groupName = "TestGroup";
        FoodGroup fGroup = new FoodGroup(1, groupName, fType);

        Ingredients ingredient1 = new Ingredients(1, "Ingredient1", fGroup);
        Ingredients ingredient2 = new Ingredients(2, "Ingredient2", fGroup);

        List<Ingredients> foodIngredientList = new ArrayList<>(Arrays.asList(ingredient1, ingredient2));
        List<Ingredients> restrictedIngredients = Collections.singletonList(ingredient1);

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodGroupRepo.findByGroupName(groupName)).thenReturn(Optional.of(fGroup));
        when(ingredienRepo.findByGroupID(fGroup.getGroupID())).thenReturn(foodIngredientList);
        when(restrictRepo.findDistinctIngredientIDsByGroupID(fGroup.getGroupID(), userID)).thenReturn(restrictedIngredients);

        // Execute
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().contains(ingredient2));
        assertFalse(response.getBody().contains(ingredient1));
    }

    @Test
    void testGetUsersByIngredients_NoRestrictedIngredients_ReturnsAllIngredients() {
        FoodType fType = new FoodType();
        
        // Mock data
        int userID = 1;
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);
        String groupName = "TestGroup";
        FoodGroup foodGroup = new FoodGroup(1, groupName, fType);

        Ingredients ingredient1 = new Ingredients(1, "Ingredient1", foodGroup);
        Ingredients ingredient2 = new Ingredients(2, "Ingredient2", foodGroup);

        List<Ingredients> foodIngredientList = new ArrayList<>(Arrays.asList(ingredient1, ingredient2));
        List<Ingredients> restrictedIngredients = Collections.emptyList();

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodGroupRepo.findByGroupName(groupName)).thenReturn(Optional.of(foodGroup));
        when(ingredienRepo.findByGroupID(foodGroup.getGroupID())).thenReturn(foodIngredientList);
        when(restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.getGroupID(), userID)).thenReturn(restrictedIngredients);

        // Execute
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains(ingredient1));
        assertTrue(response.getBody().contains(ingredient2));
    }

    @Test
    void testGetUsersByIngredients_AllRestrictedIngredients_ReturnsEmptyList() {
        // Mock data
        int userID = 1;
        String username = "username";
        UserData user = new UserData();
        user.setUserID(userID);
        user.setUsername(username);
        String groupName = "TestGroup";
        FoodType fType = new FoodType();
        FoodGroup foodGroup = new FoodGroup(1, groupName, fType);

        Ingredients ingredient1 = new Ingredients(1, "Ingredient1", foodGroup);
        Ingredients ingredient2 = new Ingredients(2, "Ingredient2", foodGroup);

        List<Ingredients> foodIngredientList = new ArrayList<>(Arrays.asList(ingredient1, ingredient2));
        List<Ingredients> restrictedIngredients = new ArrayList<>(Arrays.asList(ingredient1, ingredient2));

        // Mocking repo calls
        when(userRepo.findById(userID)).thenReturn(Optional.of(user));
        when(foodGroupRepo.findByGroupName(groupName)).thenReturn(Optional.of(foodGroup));
        when(ingredienRepo.findByGroupID(foodGroup.getGroupID())).thenReturn(foodIngredientList);
        when(restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.getGroupID(), userID)).thenReturn(restrictedIngredients);

        // Execute
        ResponseEntity<List<Ingredients>> response = controller.getUsersByIngredients(userID, groupName);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}
