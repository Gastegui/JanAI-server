package com.pbl.demo.model.hasIngredients;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "hasIngredients")
public class HasIngredients {

    int foodID;
    int ingredientID;
    

    public HasIngredients(){
    }
    

    public HasIngredients(int foodID, int ingredientID) {
        this.foodID = foodID;
        this.ingredientID = ingredientID;
    }


    public int getFoodID() {
        return foodID;
    }


    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }


    public int getIngredientID() {
        return ingredientID;
    }


    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }


    


}
