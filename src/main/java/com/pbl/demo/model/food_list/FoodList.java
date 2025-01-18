package com.pbl.demo.model.food_list;

import java.util.Date;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.user_data.UserData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
    @Column(name = "consumptionDate")
    private Date consumptionDate;
    @Id
    @Column(name = "meal")
    private String meal;

    private Float grams;

    public FoodList(){
    }

    public FoodList(UserData userData, Food food, Date consumptionDate, String meal, Float grams) {
        this.userData = userData;
        this.food = food;
        this.consumptionDate = consumptionDate;
        this.meal = meal;
        this.grams = grams;
    }

    public FoodList(Date consumptionDate, String meal, Float grams) {
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
    public Date getConsumptionDate() {
        return consumptionDate;
    }
    public void setConsumptionDate(Date consumptionDate) {
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