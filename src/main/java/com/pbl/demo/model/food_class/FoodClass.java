package com.pbl.demo.model.food_class;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.pbl.demo.model.food_type.FoodType;
import com.pbl.demo.model.restrictions.Restrictions;

@Entity
@Table(name = "foodClass")
public class FoodClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classID;

    @NotNull(message = "className can not be null")
    @Size(min = 1, max = 50, message = "The name must be between 1 and 50 characters long")
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

    
}
