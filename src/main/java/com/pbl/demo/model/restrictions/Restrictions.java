package com.pbl.demo.model.restrictions;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.pbl.demo.model.food_class.FoodClass;
import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.food_type.FoodType;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.user_data.UserData;


@Entity
@Table(name = "restrictions")
public class Restrictions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restrictionID;

    private String restrictedName;

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
    private Ingredients ingredient;

    @ManyToOne
    @JoinColumn(name = "typeID")
    private FoodType foodType;

    public Restrictions(){
    }

    

    public Restrictions(int restrictionID, String restrictedName, UserData userData, FoodGroup foodGroup,
            FoodClass foodClass, Ingredients ingredient, FoodType foodType) {
        this.restrictionID = restrictionID;
        this.restrictedName = restrictedName;
        this.userData = userData;
        this.foodGroup = foodGroup;
        this.foodClass = foodClass;
        this.ingredient = ingredient;
        this.foodType = foodType;
    }



    public Restrictions(String restrictedName, UserData userData, FoodGroup foodGroup, FoodClass foodClass,
            Ingredients ingredient, FoodType foodType) {
        this.restrictedName = restrictedName;
        this.userData = userData;
        this.foodGroup = foodGroup;
        this.foodClass = foodClass;
        this.ingredient = ingredient;
        this.foodType = foodType;
    }



    public int getRestrictionID() {
        return restrictionID;
    }

    public void setRestrictionID(int restrictionID) {
        this.restrictionID = restrictionID;
    }

    public String getRestrictedName() {
        return restrictedName;
    }

    public void setRestrictedName(String restrictedName) {
        this.restrictedName = restrictedName;
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

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredient = ingredients;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
