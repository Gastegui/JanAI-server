package com.pbl.demo.model.ingredients;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "ingredients")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientID;

    private String ingName;
    private int groupID;

    public Ingredients(){
    }

    public Ingredients(int ingredientID, String ingName, int groupID) {
        this.ingredientID = ingredientID;
        this.ingName = ingName;
        this.groupID = groupID;
    }

    public Ingredients(String ingName, int groupID) {
        this.ingName = ingName;
        this.groupID = groupID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getIngName() {
        return ingName;
    }

    public void setIngName(String ingName) {
        this.ingName = ingName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
