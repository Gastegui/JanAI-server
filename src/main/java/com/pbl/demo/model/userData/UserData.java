package com.pbl.demo.model.userData;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.weightGoals.WeightGoals;


@Entity
@Table(name = "userData")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    private String uname;
    private String secondName;
    private String gender;
    private int age;
    private int height;
    private String username;
    private String email;
    private String userPass;
    private String activityLevel;
    private Boolean premium;
    private String objective;
    private float neck;
    private float waist;
    private float hips;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightGoals> weightGoals;

    public UserData(){
    }

    public UserData(int userID, String uname, String secondName, String gender, int age, int height,
            String username, String email, String userPass, String activityLevel, Boolean premium, String objective,
            float neck, float waist, float hips) {
        this.userID = userID;
        this.uname = uname;
        this.secondName = secondName;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.username = username;
        this.email = email;
        this.userPass = userPass;
        this.activityLevel = activityLevel;
        this.premium = premium;
        this.objective = objective;
        this.neck = neck;
        this.waist = waist;
        this.hips = hips;
    }

    public UserData(String uname, String secondName, String gender, int age, int height, String username,
            String email, String userPass, String activityLevel, Boolean premium, String objective, float neck,
            float waist, float hips) {
        this.uname = uname;
        this.secondName = secondName;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.username = username;
        this.email = email;
        this.userPass = userPass;
        this.activityLevel = activityLevel;
        this.premium = premium;
        this.objective = objective;
        this.neck = neck;
        this.waist = waist;
        this.hips = hips;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getAge() {
        return age;
    }

    public List<Restrictions> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restrictions> restrictions) {
        this.restrictions = restrictions;
    }

    public List<WeightGoals> getweightGoals() {
        return weightGoals;
    }

    public void setweightGoals(List<WeightGoals> weightGoals) {
        this.weightGoals = weightGoals;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
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

    public float getNeck() {
        return neck;
    }

    public void setNeck(float neck) {
        this.neck = neck;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getHips() {
        return hips;
    }

    public void setHips(float hips) {
        this.hips = hips;
    }
}
