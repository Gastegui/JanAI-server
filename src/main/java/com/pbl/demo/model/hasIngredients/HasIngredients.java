package com.pbl.demo.model.hasIngredients;


import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.ingredients.Ingredients;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@IdClass(HasIngredients.class) // Using @IdClass with a separate class for composite key if needed
@Table(name = "hasIngredients")
public class HasIngredients {

    @Id
    @ManyToOne
    @JoinColumn(name = "foodID", referencedColumnName = "foodID") // This should match your food table's foodID column
    private Food food; // Using the Food entity directly instead of foodId

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredientID", referencedColumnName = "ingredientID") // This should match your ingredients table's ingredientID column
    private Ingredients ingredient; // Using the Ingredient entity directly instead of ingredientId

    // Default constructor
    public HasIngredients() {
    }

    // Constructor for initializing with Food and Ingredient
    public HasIngredients(Food food, Ingredients ingredient) {
        this.food = food;
        this.ingredient = ingredient;
    }

    // Getters and Setters
    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredients ingredient) {
        this.ingredient = ingredient;
    }
}
