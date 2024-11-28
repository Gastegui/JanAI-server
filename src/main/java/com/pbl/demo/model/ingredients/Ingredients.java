package com.pbl.demo.model.ingredients;

import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import jakarta.persistence.CascadeType;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.hasIngredients.HasIngredients;
import com.pbl.demo.model.restrictions.Restrictions;

@Entity
@Table(name = "ingredients")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientID;

    private String ingName;
    //private int groupID;

    @OneToMany(mappedBy = "ingredients", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HasIngredients> foodIngredients;

    @ManyToOne
    @JoinColumn(name = "restrictionID")
    private Restrictions restrictions;

    public Ingredients(){
    }

    public Ingredients(int ingredientID, String ingName, int groupID) {
        this.ingredientID = ingredientID;
        this.ingName = ingName;
        //this.groupID = groupID;
    }

    public Ingredients(String ingName, int groupID) {
        this.ingName = ingName;
        //this.groupID = groupID;
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

    /*public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }*/
}
