package com.pbl.demo.model.restrictions;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.userData.UserData;


@Entity
@Table(name = "restrictions")
public class Restrictions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restrictionID;

    private String restrictedFood;

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userData;

    @ManyToOne
    @JoinColumn(name = "groupID")
    private FoodGroup foodGroup;

    @ManyToOne
    @JoinColumn(name = "classID")
    private FoodClass foodClass;

    @ManyToOne
    @JoinColumn(name = "ingredientID")
    private Ingredients ingredients;

    @ManyToOne
    @JoinColumn(name = "typeID")
    private FoodType foodType;

    public Restrictions(){
    }

    public Restrictions(int restrictionID, String restrictedFood, UserData userData, FoodGroup foodGroup, FoodClass foodClass,
            Ingredients ingredients, FoodType foodType) {
        this.restrictionID = restrictionID;
        this.restrictedFood = restrictedFood;
        this.userData = userData;
        this.foodGroup = foodGroup;
        this.foodClass = foodClass;
        this.ingredients = ingredients;
        this.foodType = foodType;
    }

    public Restrictions(String restrictedFood, UserData userData, FoodGroup foodGroup, FoodClass foodClass,
            Ingredients ingredients, FoodType foodType) {
        this.restrictedFood = restrictedFood;
        this.userData = userData;
        this.foodGroup = foodGroup;
        this.foodClass = foodClass;
        this.ingredients = ingredients;
        this.foodType = foodType;
    }

    public int getRestrictionID() {
        return restrictionID;
    }

    public void setRestrictionID(int restrictionID) {
        this.restrictionID = restrictionID;
    }

    public String getRestrictedFood() {
        return restrictedFood;
    }

    public void setRestrictedFood(String restrictedFood) {
        this.restrictedFood = restrictedFood;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public FoodGroup getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(FoodGroup foodGroup) {
        this.foodGroup = foodGroup;
    }

    public FoodClass getFoodClass() {
        return foodClass;
    }

    public void setFoodClass(FoodClass foodClass) {
        this.foodClass = foodClass;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    
}
