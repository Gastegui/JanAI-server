package com.pbl.demo.model.restrictions;

import java.util.List;

import com.pbl.demo.model.hasIngredients.HasIngredients;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.pbl.demo.model.users.Users;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.ingredients.Ingredients;


@Entity
@Table(name = "restrictions")
public class Restrictions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int restrictionID;

    private String restrictedFood;
    /*private int userID;
    private int groupID;
    private int typeID;
    private int classID;
    private int ingredientID;*/

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "groupID")
    private FoodGroup foodGroup;

    @ManyToOne
    @JoinColumn(name = "classID")
    private FoodClass foodClass;

    @OneToMany(mappedBy = "ingredients", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredients> ingredients;

    public Restrictions(){
    }

    public Restrictions(int restrictionID, String restrictedFood, int userID, int groupID, int typeID, int classID,
            int ingredientID) {
        this.restrictionID = restrictionID;
        this.restrictedFood = restrictedFood;
        /*this.userID = userID;
        this.groupID = groupID;
        this.typeID = typeID;
        this.classID = classID;
        this.ingredientID = ingredientID;*/
    }

    public Restrictions(String restrictedFood, int userID, int groupID, int typeID, int classID, int ingredientID) {
        this.restrictedFood = restrictedFood;
        /*this.userID = userID;
        this.groupID = groupID;
        this.typeID = typeID;
        this.classID = classID;
        this.ingredientID = ingredientID;*/
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

    /*public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }*/
}
