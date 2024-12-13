package com.pbl.demo.model.foodList;
import java.io.Serializable;

public class FoodListPK implements Serializable {
    private int food;
    private int user;

    // Constructor vacío
    public FoodListPK() {}

    // Constructor completo
    public FoodListPK(int food, int user) {
        this.food = food;
        this.user = user;
    }

    
}