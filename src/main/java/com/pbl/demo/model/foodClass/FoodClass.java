package com.pbl.demo.model.foodClass;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "foodClass")
public class FoodClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    int classID;
    String className;
    

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



}
