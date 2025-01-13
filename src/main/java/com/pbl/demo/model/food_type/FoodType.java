package com.pbl.demo.model.food_type;

import java.util.List;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.food_class.FoodClass;
import com.pbl.demo.model.food_group.FoodGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "foodType")
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeID;

    private String typeName;
    
    @OneToMany(mappedBy = "foodType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @ManyToOne
    @JoinColumn(name = "classID")
    private FoodClass foodClass;

    @OneToMany(mappedBy = "foodType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodGroup> foodGroups;

    public FoodType(){
    }

    


    public FoodType(int typeID, String typeName, FoodClass foodClass) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.foodClass = foodClass;
    }

    public FoodType(String typeName, FoodClass foodClass) {
        this.typeName = typeName;
        this.foodClass = foodClass;
    }




    public int getTypeId() {
        return typeID;
    }

    public void setTypeId(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public FoodClass getFoodClass() {
        return foodClass;
    }

    public void setFoodClass(FoodClass foodClass) {
        this.foodClass = foodClass;
    }

    
}
