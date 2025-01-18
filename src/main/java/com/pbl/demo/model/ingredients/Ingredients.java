package com.pbl.demo.model.ingredients;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import jakarta.persistence.CascadeType;

import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.has_ingredients.HasIngredients;
import com.pbl.demo.model.restrictions.Restrictions;

@Entity
@Table(name = "ingredients")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientID;

    @NotNull(message = "The ingName can not be null")
    @Size(min = 1, max = 50, message = "ingName must have between 1 and 50 characters long")
    private String ingName;

    @NotNull(message = "The groupID can not be null")
    @ManyToOne
    @JoinColumn(name = "groupID")
    private FoodGroup groupID;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HasIngredients> foodIngredients;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    public Ingredients(){
    }

    

    public Ingredients(int ingredientID, String ingName, FoodGroup groupID) {
        this.ingredientID = ingredientID;
        this.ingName = ingName;
        this.groupID = groupID;
    }



    public Ingredients(String ingName, FoodGroup groupID) {
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



    public FoodGroup getGroupID() {
        return groupID;
    }



    public void setGroupID(FoodGroup groupID) {
        this.groupID = groupID;
    }
    
}
