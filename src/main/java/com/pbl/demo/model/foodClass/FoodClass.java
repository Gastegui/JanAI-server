package com.pbl.demo.model.foodClass;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.foodType.FoodType;

@Entity
@Table(name = "foodClass")
public class FoodClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classID;

    private String className;
    
    @OneToMany(mappedBy = "foodClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @OneToMany(mappedBy = "foodClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodType> foodType;

    public FoodClass(){
    }

    public FoodClass(int classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public FoodClass(String className) {
        this.className = className;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /*public List<FoodType> getFoodType() {
        return foodType;
    }*/
    
}
