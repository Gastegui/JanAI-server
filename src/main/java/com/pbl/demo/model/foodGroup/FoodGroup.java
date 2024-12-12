package com.pbl.demo.model.foodGroup;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.ingredients.Ingredients;

@Entity
@Table(name = "foodGroup")
public class FoodGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupID;

    private String groupName;

    @OneToMany(mappedBy = "foodGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @ManyToOne
    @JoinColumn(name = "typeID")
    private FoodType foodType;

    @OneToMany(mappedBy = "groupID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredients> ingredients;

    public FoodGroup(){
    }

    public FoodGroup(int groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public FoodGroup(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
