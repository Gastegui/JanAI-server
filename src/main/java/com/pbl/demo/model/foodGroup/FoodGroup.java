package com.pbl.demo.model.foodGroup;

import java.util.List;

import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.ingredients.Ingredients;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "foodGroup")
public class FoodGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupID;

    private int typeID;
    private String groupName;

    @OneToMany(mappedBy = "Ingredients", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredients> ingredients;

    public FoodGroup(){
    }

    public FoodGroup(int groupID, int typeID, String groupName) {
        this.groupID = groupID;
        this.typeID = typeID;
        this.groupName = groupName;
    }

    public FoodGroup(int typeID, String groupName) {
        this.typeID = typeID;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
