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
    /*private Float recommendedCalories;
    private Float recommendedProteins;
    private Float recommendedCarbs;
    private Float recommendedFats;
    private Float recommendedFiber;*/

    public WeightGoals(){
    }

    public WeightGoals(int weightGoalsID, Float weight, Float goalWeight,
            int durationToAchieveGoalWeight, Date registerDate) { //Float recommendedCalories, Float recommendedProteins, Float recommendedCarbs, Float recommendedFats, Float recommendedFiber
                
        this.weightGoalsID = weightGoalsID;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
        this.registerDate = registerDate;
        /*this.recommendedCalories = recommendedCalories;
        this.recommendedProteins = recommendedProteins;
        this.recommendedCarbs = recommendedCarbs;
        this.recommendedFats = recommendedFats;
        this.recommendedFiber = recommendedFiber;*/
    }

    public WeightGoals(Float weight, Float goalWeight, int durationToAchieveGoalWeight,
            Date registerDate) { //, Float recommendedCalories, Float recommendedProteins, Float recommendedCarbs, Float recommendedFats, Float recommendedFiber
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
        this.registerDate = registerDate;
        /*this.recommendedCalories = recommendedCalories;
        this.recommendedProteins = recommendedProteins;
        this.recommendedCarbs = recommendedCarbs;
        this.recommendedFats = recommendedFats;
        this.recommendedFiber = recommendedFiber;*/
    }

    public int getWeightGoalsID() {
        return weightGoalsID;
    }

    public void setWeightGoalsID(int weightGoalsID) {
        this.weightGoalsID = weightGoalsID;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
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

    /*public Float getRecommendedCalories() {
        return recommendedCalories;
    }

    public void setRecommendedCalories(Float recommendedCalories) {
        this.recommendedCalories = recommendedCalories;
    }

    public Float getRecommendedProteins() {
        return recommendedProteins;
    }

    public void setRecommendedProteins(Float recommendedProteins) {
        this.recommendedProteins = recommendedProteins;
    }

    public Float getRecommendedCarbs() {
        return recommendedCarbs;
    }

    public void setRecommendedCarbs(Float recommendedCarbs) {
        this.recommendedCarbs = recommendedCarbs;
    }

    public Float getRecommendedFats() {
        return recommendedFats;
    }

    public void setRecommendedFats(Float recommendedFats) {
        this.recommendedFats = recommendedFats;
    }

    public Float getRecommendedFiber() {
        return recommendedFiber;
    }

    public void setRecommendedFiber(Float recommendedFiber) {
        this.recommendedFiber = recommendedFiber;
    }*/

    
}