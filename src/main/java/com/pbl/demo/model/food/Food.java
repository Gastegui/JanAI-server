package com.pbl.demo.model.food;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.persistence.CascadeType;


import com.pbl.demo.model.hasIngredients.HasIngredients;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodID;

    private String foodName;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HasIngredients> foodTypes;

    public Food(){
    }

    public Food(int foodID, String foodName) {
        this.foodID = foodID;
        this.foodName = foodName;
    }

    public Food(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
