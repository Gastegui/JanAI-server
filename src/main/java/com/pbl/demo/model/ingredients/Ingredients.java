package com.pbl.demo.model.ingredients;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import jakarta.persistence.CascadeType;

import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.hasIngredients.HasIngredients;
import com.pbl.demo.model.restrictions.Restrictions;

@Entity
@Table(name = "ingredients")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientID;

    private String ingName;

    @ManyToOne
    @JoinColumn(name = "groupID")
    private FoodGroup groupID;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HasIngredients> foodIngredients;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    public Ingredients(){
    }

    public Ingredients(int ingredientID, String ingName) {
        this.ingredientID = ingredientID;
        this.ingName = ingName;
    }

    public Ingredients(String ingName) {
        this.ingName = ingName;
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
}
