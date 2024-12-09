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

    
    private Double cur_weight;
    private Double goal_weight;
    private Date goalDate;
    private Date registerDate;

    public WeightGoals(){
    }

    

    



    public WeightGoals(int weightGoalsID, UserData userData, Double cur_weight, Double goal_weight, Date goalDate,
            Date registerDate) {
        this.weightGoalsID = weightGoalsID;
        this.userData = userData;
        this.cur_weight = cur_weight;
        this.goal_weight = goal_weight;
        this.goalDate = goalDate;
        this.registerDate = registerDate;
    }



    public WeightGoals(UserData userData, Double cur_weight, Double goal_weight, Date goalDate, Date registerDate) {
        this.userData = userData;
        this.cur_weight = cur_weight;
        this.goal_weight = goal_weight;
        this.goalDate = goalDate;
        this.registerDate = registerDate;
    }







    public int getWeightGoalsID() {
        return weightGoalsID;
    }



    public void setWeightGoalsID(int weightGoalsID) {
        this.weightGoalsID = weightGoalsID;
    }



    



    public Double getCur_weight() {
        return cur_weight;
    }



    public void setCur_weight(Double cur_weight) {
        this.cur_weight = cur_weight;
    }



    public Double getGoal_weight() {
        return goal_weight;
    }



    public void setGoal_weight(Double goal_weight) {
        this.goal_weight = goal_weight;
    }



    public Date getGoalDate() {
        return goalDate;
    }



    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }



    public Date getRegisterDate() {
        return registerDate;
    }



    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }







    public UserData getUserData() {
        return userData;
    }







    public void setUserData(UserData userData) {
        this.userData = userData;
    }




}
