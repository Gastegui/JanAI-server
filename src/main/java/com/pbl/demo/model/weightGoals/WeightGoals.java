package com.pbl.demo.model.weightGoals;

import java.util.Date;


import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.userData.UserData;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "weightGoals")
public class WeightGoals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int weightGoalsID;


    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userData;

    
    private Float weight;
    private Float goalWeight;
    private int durationToAchieveGoalWeight;
    private Date registerDate;

    public WeightGoals(){
    }


    public WeightGoals(int weightGoalsID, Float weight, Float goalWeight,
            int durationToAchieveGoalWeight, Date registerDate) {
        this.weightGoalsID = weightGoalsID;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
        this.registerDate = registerDate;
    }


    public WeightGoals(Float weight, Float goalWeight, int durationToAchieveGoalWeight,
            Date registerDate) {
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
        this.registerDate = registerDate;
    }



    public int getWeightGoalsID() {
        return weightGoalsID;
    }


    public void setWeightGoalsID(int weightGoalsID) {
        this.weightGoalsID = weightGoalsID;
    }


    public Float getWeight() {
        return weight;
    }


    public void setWeight(Float weight) {
        this.weight = weight;
    }


    public Float getGoalWeight() {
        return goalWeight;
    }


    public void setGoalWeight(Float goalWeight) {
        this.goalWeight = goalWeight;
    }


    public int getDurationToAchieveGoalWeight() {
        return durationToAchieveGoalWeight;
    }


    public void setDurationToAchieveGoalWeight(int durationToAchieveGoalWeight) {
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
    }


    public Date getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }


    public void setUserData(UserData userData) {
        this.userData = userData;
    }


}