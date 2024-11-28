package com.pbl.demo.model.foodType;

import java.util.Date;
import java.util.List;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.foodGroup.FoodGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "foodType")
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    int typeId;
    //int classID;
    String typeName;
    
    @OneToMany(mappedBy = "foodGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodGroup> foodGroups;

    public FoodType(){
    }

    public FoodType(int typeId, int classID, String typeName) {
        this.typeId = typeId;
        //this.classID = classID;
        this.typeName = typeName;
    }

    public FoodType(int classID, String typeName) {
        //this.classID = classID;
        this.typeName = typeName;
    }



    public int getTypeId() {
        return typeId;
    }



    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }



    /*public int getClassID() {
        return classID;
    }



    public void setClassID(int classID) {
        this.classID = classID;
    }*/



    public String getTypeName() {
        return typeName;
    }



    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    
}
