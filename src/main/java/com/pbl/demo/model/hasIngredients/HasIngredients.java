package com.pbl.demo.model.hasIngredients;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.ingredients.Ingredients;


@Entity
@Table(name = "hasIngredients")
public class HasIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne
    @JoinColumn(name = "foodID")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "ingredientID")
    private Ingredients ingredients;

    public HasIngredients(){
    }

    public HasIngredients(int ID, Food food, Ingredients ingredients) {
        this.ID = ID;
        this.food = food;
        this.ingredients = ingredients;
    }

    public HasIngredients(Food food, Ingredients ingredients) {
        this.food = food;
        this.ingredients = ingredients;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }
}
