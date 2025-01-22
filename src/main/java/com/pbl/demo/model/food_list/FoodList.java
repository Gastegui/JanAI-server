package com.pbl.demo.model.food_list;

import java.time.LocalDate;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.user_data.UserData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotNull;


@Entity
@IdClass(FoodList.class)
@Table(name = "foodList")
public class FoodList {

    @Id
    @ManyToOne
    @JoinColumn(name = "foodID")
    private Food food;
    @Id
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userData;

    @Id
    @NotNull(message = "The typeName can not be null")
    @FutureOrPresent(message = "The registerDate must be today or a future date")
    @Column(name = "consumptionDate")
    private LocalDate consumptionDate;
    
    @Id
    @NotNull(message = "The meal can not be null")
    @Size(min = 1, max = 50, message = "The meal must be between 1 and 50 characters long")
    @Column(name = "meal")
    private String meal;

    @NotNull(message = "The grams can not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "The grams measurement must be greater than 0")
    private Float grams;

    public FoodList(){
    }

    public FoodList(UserData userData, Food food, LocalDate consumptionDate, String meal, Float grams) {
        this.userData = userData;
        this.food = food;
        this.consumptionDate = consumptionDate;
        this.meal = meal;
        this.grams = grams;
    }

    public FoodList(LocalDate consumptionDate, String meal, Float grams) {
        this.consumptionDate = consumptionDate;
        this.meal = meal;
        this.grams = grams;
    }

    public UserData getUserData() {
        return userData;
    }
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
    public Food getFood() {
        return food;
    }
    public void setFood(Food food) {
        this.food = food;
    }
    public LocalDate getConsumptionDate() {
        return consumptionDate;
    }
    public void setConsumptionDate(LocalDate consumptionDate) {
        this.consumptionDate = consumptionDate;
    }
    public String getMeal() {
        return meal;
    }
    public void setMeal(String meal) {
        this.meal = meal;
    }
    public Float getGrams() {
        return grams;
    }
    public void setGrams(Float grams) {
        this.grams = grams;
    }
}