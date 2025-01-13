package com.pbl.demo.controller.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;

public class IngredientControllerTest {
    @InjectMocks
    IngredientController ingredientController;

    @Mock
    IngredientsRepository ingRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllIngredients_NotFound(){
        when(ingRepo.findAll()).thenReturn(List.of());

        ResponseEntity<List<Ingredients>> response = ingredientController.getAllIngredients();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllIngredients_IngredientsExist() {
        // Arrange
        List<Ingredients> groups = List.of(new Ingredients());
        when(ingRepo.findAll()).thenReturn(groups);

        // Act
        ResponseEntity<List<Ingredients>> response = ingredientController.getAllIngredients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groups, response.getBody());
    }

    @Test
    void getIngredientByName_NotFound() {
        // Arrange
        String ingName = "Unicorn meat";
        when(ingRepo.findByIngName(ingName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Ingredients> response = ingredientController.getIngredientByName(ingName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getIngredientByName_IngredientFound() {
        // Arrange
        String ingName = "Beef";
        Ingredients ingredient = new Ingredients();
        when(ingRepo.findByIngName(ingName)).thenReturn(Optional.of(ingredient));

        // Act
        ResponseEntity<Ingredients> response = ingredientController.getIngredientByName(ingName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredient, response.getBody());
    }

   
}
