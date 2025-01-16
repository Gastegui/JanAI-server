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

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String ingName;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
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
