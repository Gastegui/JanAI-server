package com.pbl.demo.model.users;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    int userId;
    String uname;
    String secondName;
    String gender;
    Date birthdate;
    int height;
    String username;
    String email;
    String userPass;
    String activity;
    Boolean premium;
    String objective;
    double neck;
    double waist;
    double hips;

    public Users(){
    }

    public Users(int userId, String uname, String secondName, String gender, Date birthdate, int height,
            String username, String email, String userPass, String activity, Boolean premium, String objective,
            double neck, double waist, double hips) {
        this.userId = userId;
        this.uname = uname;
        this.secondName = secondName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.username = username;
        this.email = email;
        this.userPass = userPass;
        this.activity = activity;
        this.premium = premium;
        this.objective = objective;
        this.neck = neck;
        this.waist = waist;
        this.hips = hips;
    }

    public Users(String uname, String secondName, String gender, Date birthdate, int height, String username,
            String email, String userPass, String activity, Boolean premium, String objective, double neck,
            double waist, double hips) {
        this.uname = uname;
        this.secondName = secondName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.username = username;
        this.email = email;
        this.userPass = userPass;
        this.activity = activity;
        this.premium = premium;
        this.objective = objective;
        this.neck = neck;
        this.waist = waist;
        this.hips = hips;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

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

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
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

    public double getHips() {
        return hips;
    }

    public void setHips(double hips) {
        this.hips = hips;
    }
}
