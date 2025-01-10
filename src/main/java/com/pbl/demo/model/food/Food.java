package com.pbl.demo.model.food;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.persistence.CascadeType;

import com.pbl.demo.model.foodList.FoodList;
import com.pbl.demo.model.hasIngredients.HasIngredients;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodID;

    private String foodName;
    private float proteins;
    private float carbs;
    private float fats;
    private float fiber;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HasIngredients> foodTypes;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodList> foodList;

    public Food(){}

    public Food(int foodID, String foodName, float proteins, float carbs, float fats, float fiber) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
    }

    public Food(String foodName, float proteins, float carbs, float fats, float fiber) {
        this.foodName = foodName;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
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

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

}
