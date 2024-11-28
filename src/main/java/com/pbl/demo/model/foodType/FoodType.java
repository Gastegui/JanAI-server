package com.pbl.demo.model.foodType;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import com.pbl.demo.model.restrictions.*;

@Entity
@Table(name = "foodType")
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int typeId;
    //int classID;
    private String typeName;
    
    @OneToMany(mappedBy = "Restrictions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @ManyToOne
    @JoinColumn(name = "classID")
    private int classID;

    public FoodType(){
    }

    

    public FoodType(int typeId, int classID, String typeName) {
        this.typeId = typeId;
        this.classID = classID;
        this.typeName = typeName;
    }



    public FoodType(int classID, String typeName) {
        this.classID = classID;
        this.typeName = typeName;
    }



    public int getTypeId() {
        return typeId;
    }



    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }



    public int getClassID() {
        return classID;
    }



    public void setClassID(int classID) {
        this.classID = classID;
    }



    public String getTypeName() {
        return typeName;
    }



    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    
}
