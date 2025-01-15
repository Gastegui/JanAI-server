package com.pbl.demo.model.weight_goals;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pbl.demo.model.user_data.UserData;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "weightGoals")
public class WeightGoals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int weightGoalsID;


    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userData;

    @NotNull(message = "El nombre no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño del cuello debe ser mayor que 0")
    private Float weight;

    @NotNull(message = "El nombre no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño del cuello debe ser mayor que 0")
    private Float goalWeight;

    @NotNull(message = "El nombre no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño del cuello debe ser mayor que 0")
    private int durationToAchieveGoalWeight;

    @NotNull(message = "La fecha no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;


    public WeightGoals(){
    }

    

    public WeightGoals(int weightGoalsID, UserData userData, Float weight, Float goalWeight,
            int durationToAchieveGoalWeight, LocalDate registerDate) {
        this.weightGoalsID = weightGoalsID;
        this.userData = userData;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.durationToAchieveGoalWeight = durationToAchieveGoalWeight;
        this.registerDate = registerDate;
    }



    public WeightGoals(UserData userData, Float weight, Float goalWeight, int durationToAchieveGoalWeight,
            LocalDate registerDate) {
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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

}