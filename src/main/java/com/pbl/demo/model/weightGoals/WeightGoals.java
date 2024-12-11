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

    
    private Double weight;
    private Double goalWeight;
    private Date durationToAchieveGoalWeight;
    private Date registerDate;


    public WeightGoals(){
    }

    public WeightGoals(int weightGoalsID, UserData userData, Double weight, Double goalWeight,
            Date durationToAchieveGoalWeight, Date registerDate) {
        this.weightGoalsID = weightGoalsID;
        this.userData = userData;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
        this.registerDate = registerDate;
    }

    public WeightGoals(UserData userData, Double weight, Double goalWeight, Date durationToAchieveGoalWeight,
            Date registerDate) {
        this.userData = userData;
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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(Double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public Date getDurationToAchieveGoalWeight() {
        return durationToAchieveGoalWeight;
    }

    public void setDurationToAchieveGoalWeight(Date durationToAchieveGoalWeight) {
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    
}
