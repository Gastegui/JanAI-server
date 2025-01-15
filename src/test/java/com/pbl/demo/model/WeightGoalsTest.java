package com.pbl.demo.model;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.weight_goals.WeightGoals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WeightGoalsTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        WeightGoals weightGoal = new WeightGoals();

        // Assert
        assertNotNull(weightGoal);
        assertEquals(0, weightGoal.getWeightGoalsID());
        assertNull(weightGoal.getUserData());
        assertNull(weightGoal.getWeight());
        assertNull(weightGoal.getGoalWeight());
        assertEquals(0, weightGoal.getDurationToAchieveGoalWeight());
        assertNull(weightGoal.getRegisterDate());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        int weightGoalsID = 1;
        UserData userData = new UserData();
        Float weight = 70.5f;
        Float goalWeight = 65.0f;
        int duration = 90;
        LocalDate registerDate = LocalDate.now();

        // Act
        WeightGoals weightGoal = new WeightGoals(weightGoalsID, userData, weight, goalWeight, duration, registerDate);

        // Assert
        assertEquals(weightGoalsID, weightGoal.getWeightGoalsID());
        assertEquals(userData, weightGoal.getUserData());
        assertEquals(weight, weightGoal.getWeight());
        assertEquals(goalWeight, weightGoal.getGoalWeight());
        assertEquals(duration, weightGoal.getDurationToAchieveGoalWeight());
        assertEquals(registerDate, weightGoal.getRegisterDate());
    }

    @Test
    void testConstructorWithPartialParameters() {
        // Arrange
        UserData userData = new UserData();
        Float weight = 75.0f;
        Float goalWeight = 70.0f;
        int duration = 60;
        LocalDate registerDate = LocalDate.now();

        // Act
        WeightGoals weightGoal = new WeightGoals(userData, weight, goalWeight, duration, registerDate);

        // Assert
        assertEquals(0, weightGoal.getWeightGoalsID());
        assertEquals(userData, weightGoal.getUserData());
        assertEquals(weight, weightGoal.getWeight());
        assertEquals(goalWeight, weightGoal.getGoalWeight());
        assertEquals(duration, weightGoal.getDurationToAchieveGoalWeight());
        assertEquals(registerDate, weightGoal.getRegisterDate());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        WeightGoals weightGoal = new WeightGoals();
        int weightGoalsID = 2;
        UserData userData = new UserData();
        Float weight = 80.0f;
        Float goalWeight = 75.0f;
        int duration = 30;
        LocalDate registerDate = LocalDate.now();

        // Act
        weightGoal.setWeightGoalsID(weightGoalsID);
        weightGoal.setUserData(userData);
        weightGoal.setWeight(weight);
        weightGoal.setGoalWeight(goalWeight);
        weightGoal.setDurationToAchieveGoalWeight(duration);
        weightGoal.setRegisterDate(registerDate);

        // Assert
        assertEquals(weightGoalsID, weightGoal.getWeightGoalsID());
        assertEquals(userData, weightGoal.getUserData());
        assertEquals(weight, weightGoal.getWeight());
        assertEquals(goalWeight, weightGoal.getGoalWeight());
        assertEquals(duration, weightGoal.getDurationToAchieveGoalWeight());
        assertEquals(registerDate, weightGoal.getRegisterDate());
    }

    @Test
    void testSettersWithNullValues() {
        // Arrange
        WeightGoals weightGoal = new WeightGoals();

        // Act
        weightGoal.setUserData(null);
        weightGoal.setWeight(null);
        weightGoal.setGoalWeight(null);
        weightGoal.setRegisterDate(null);

        // Assert
        assertNull(weightGoal.getUserData());
        assertNull(weightGoal.getWeight());
        assertNull(weightGoal.getGoalWeight());
        assertNull(weightGoal.getRegisterDate());
    }

    @Test
    void testExtremeValues() {
        // Arrange
        WeightGoals weightGoal = new WeightGoals();
        Float extremeWeight = Float.MAX_VALUE;
        Float extremeGoalWeight = Float.MIN_VALUE;
        int extremeDuration = Integer.MAX_VALUE;

        // Act
        weightGoal.setWeight(extremeWeight);
        weightGoal.setGoalWeight(extremeGoalWeight);
        weightGoal.setDurationToAchieveGoalWeight(extremeDuration);

        // Assert
        assertEquals(extremeWeight, weightGoal.getWeight());
        assertEquals(extremeGoalWeight, weightGoal.getGoalWeight());
        assertEquals(extremeDuration, weightGoal.getDurationToAchieveGoalWeight());
    }

    @Test
    void testDateBehavior() {
        // Arrange
        WeightGoals weightGoal = new WeightGoals();
        LocalDate currentDate = LocalDate.now();

        // Act
        weightGoal.setRegisterDate(currentDate);

        // Assert
        assertEquals(currentDate, weightGoal.getRegisterDate());
    }
}
