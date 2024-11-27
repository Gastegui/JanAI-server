package com.pbl.demo.model.weightGoals;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "weightGoals")
public class WeightGoals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int weightGoalsID;
    private int userID;
    private Double cur_weight;
    private Double goal_weight;
    private Date goalDate;
    private Date registerDate;

    public WeightGoals(){
    }

    

    public WeightGoals(int weightGoalsID, int userID, Double cur_weight, Double goal_weight, Date goalDate,
            Date registerDate) {
        this.weightGoalsID = weightGoalsID;
        this.userID = userID;
        this.cur_weight = cur_weight;
        this.goal_weight = goal_weight;
        this.goalDate = goalDate;
        this.registerDate = registerDate;
    }



    public WeightGoals(int userID, Double cur_weight, Double goal_weight, Date goalDate, Date registerDate) {
        this.userID = userID;
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



    public int getUserID() {
        return userID;
    }



    public void setUserID(int userID) {
        this.userID = userID;
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




}
