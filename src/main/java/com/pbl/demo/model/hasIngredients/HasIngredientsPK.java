package com.pbl.demo.model.hasIngredients;
import java.io.Serializable;

public class HasIngredientsPK implements Serializable {
    private int food;
    private int ingredient;

    // Constructor vacío
    public HasIngredientsPK() {}

    // Constructor completo
    public HasIngredientsPK(int food, int ingredient) {
        this.food = food;
        this.ingredient = ingredient;
    }

    
}