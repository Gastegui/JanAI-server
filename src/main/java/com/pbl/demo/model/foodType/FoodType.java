package com.pbl.demo.model.foodType;

import java.util.Date;
import java.util.List;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodGroup.FoodGroup;

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

    public FoodType(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public FoodType(String typeName) {
        this.typeName = typeName;
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
}
