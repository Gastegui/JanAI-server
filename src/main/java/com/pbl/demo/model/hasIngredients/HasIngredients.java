package com.pbl.demo.model.hasIngredients;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.ingredients.Ingredients;


@Entity
@IdClass(HasIngredients.class)
@Table(name = "hasIngredients")
public class HasIngredients {

    @Id
    @ManyToOne
    @JoinColumn(name = "foodID")
    private Food food;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredientID")
    private Ingredients ingredients;

    public HasIngredients(){
    }

    public HasIngredients(Food food, Ingredients ingredients) {
        this.food = food;
        this.ingredients = ingredients;
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
