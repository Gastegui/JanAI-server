package com.pbl.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    int userId;
    String name;
    String secondName;
    String gender;
    Date birthdate;
    int height;
    int weight;
    String username;
    String email;
    String password;
    //int restrictionsID;
    String activity;
    Boolean premium;
    double neck;
    double waist;

    public User(){

    }

    
    

    public User(int userId, String name, String secondName, String gender, Date birthdate, int height, int weight,
            String username, String email, String password, String activity, Boolean premium,
            double neck, double waist) {
        this.userId = userId;
        this.name = name;
        this.secondName = secondName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.username = username;
        this.email = email;
        this.password = password;
        //this.restrictionsID = restrictionsID;
        this.activity = activity;
        this.premium = premium;
        this.neck = neck;
        this.waist = waist;
    }





    public User(String name, String secondName, String gender, Date birthdate, int height, int weight, String username,
            String email, String password, String activity, Boolean premium, double neck,
            double waist) {
        this.name = name;
        this.secondName = secondName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.username = username;
        this.email = email;
        this.password = password;
        //this.restrictionsID = restrictionsID;
        this.activity = activity;
        this.premium = premium;
        this.neck = neck;
        this.waist = waist;
    }




    public int getUserId() {
        return userId;
    }




    public void setUserId(int userId) {
        this.userId = userId;
    }




    public String getName() {
        return name;
    }




    public void setName(String name) {
        this.name = name;
    }




    public String getSecondName() {
        return secondName;
    }




    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }




    public String getGender() {
        return gender;
    }




    public void setGender(String gender) {
        this.gender = gender;
    }




    public Date getBirthdate() {
        return birthdate;
    }




    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }




    public int getHeight() {
        return height;
    }




    public void setHeight(int height) {
        this.height = height;
    }




    public int getWeight() {
        return weight;
    }




    public void setWeight(int weight) {
        this.weight = weight;
    }




    public String getUsername() {
        return username;
    }




    public void setUsername(String username) {
        this.username = username;
    }




    public String getEmail() {
        return email;
    }




    public void setEmail(String email) {
        this.email = email;
    }




    public String getPassword() {
        return password;
    }




    public void setPassword(String password) {
        this.password = password;
    }




    /*public int getRestrictionsID() {
        return restrictionsID;
    }




    public void setRestrictionsID(int restrictionsID) {
        this.restrictionsID = restrictionsID;
    }*/




    public String getActivity() {
        return activity;
    }




    public void setActivity(String activity) {
        this.activity = activity;
    }




    public Boolean getPremium() {
        return premium;
    }




    public void setPremium(Boolean premium) {
        this.premium = premium;
    }




    public double getNeck() {
        return neck;
    }




    public void setNeck(double neck) {
        this.neck = neck;
    }




    public double getWaist() {
        return waist;
    }




    public void setWaist(double waist) {
        this.waist = waist;
    }





    




    
    
}
