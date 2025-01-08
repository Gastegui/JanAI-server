package com.pbl.demo.model;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.weightGoals.WeightGoals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        UserData userData = new UserData();

        // Assert
        assertNotNull(userData);
        assertEquals(0, userData.getUserID());
        assertNull(userData.getUname());
        assertNull(userData.getSecondName());
        assertNull(userData.getGender());
        assertEquals(0, userData.getAge());
        assertEquals(0, userData.getHeight());
        assertNull(userData.getUsername());
        assertNull(userData.getEmail());
        assertNull(userData.getUserPass());
        assertNull(userData.getActivityLevel());
        assertNull(userData.getPremium());
        assertNull(userData.getObjective());
        assertEquals(0, userData.getNeck());
        assertEquals(0, userData.getWaist());
        assertEquals(0, userData.getHips());
        assertNull(userData.getRestrictions());
        assertNotNull(userData.getWeightGoals());
        assertTrue(userData.getWeightGoals().isEmpty());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        int userID = 1;
        String uname = "John";
        String secondName = "Doe";
        String gender = "Male";
        int age = 30;
        int height = 180;
        String username = "johndoe";
        String email = "john.doe@example.com";
        String userPass = "password123";
        String activityLevel = "Active";
        Boolean premium = true;
        String objective = "Lose weight";
        float neck = 15.5f;
        float waist = 32.0f;
        float hips = 40.0f;

        // Act
        UserData userData = new UserData(userID, uname, secondName, gender, age, height, username, email, userPass,
                activityLevel, premium, objective, neck, waist, hips);

        // Assert
        assertEquals(userID, userData.getUserID());
        assertEquals(uname, userData.getUname());
        assertEquals(secondName, userData.getSecondName());
        assertEquals(gender, userData.getGender());
        assertEquals(age, userData.getAge());
        assertEquals(height, userData.getHeight());
        assertEquals(username, userData.getUsername());
        assertEquals(email, userData.getEmail());
        assertEquals(userPass, userData.getUserPass());
        assertEquals(activityLevel, userData.getActivityLevel());
        assertEquals(premium, userData.getPremium());
        assertEquals(objective, userData.getObjective());
        assertEquals(neck, userData.getNeck(), 0.01);
        assertEquals(waist, userData.getWaist(), 0.01);
        assertEquals(hips, userData.getHips(), 0.01);
    }

    @Test
    void testConstructorWithPartialParameters() {
        // Arrange
        String uname = "Jane";
        String secondName = "Doe";
        String gender = "Female";
        int age = 25;
        int height = 170;
        String username = "janedoe";
        String email = "jane.doe@example.com";
        String userPass = "password123";
        String activityLevel = "Moderate";
        Boolean premium = false;
        String objective = "Gain muscle";
        float neck = 13.5f;
        float waist = 28.0f;
        float hips = 37.0f;

        // Act
        UserData userData = new UserData(uname, secondName, gender, age, height, username, email, userPass,
                activityLevel, premium, objective, neck, waist, hips);

        // Assert
        assertEquals(uname, userData.getUname());
        assertEquals(secondName, userData.getSecondName());
        assertEquals(gender, userData.getGender());
        assertEquals(age, userData.getAge());
        assertEquals(height, userData.getHeight());
        assertEquals(username, userData.getUsername());
        assertEquals(email, userData.getEmail());
        assertEquals(userPass, userData.getUserPass());
        assertEquals(activityLevel, userData.getActivityLevel());
        assertEquals(premium, userData.getPremium());
        assertEquals(objective, userData.getObjective());
        assertEquals(neck, userData.getNeck(), 0.01);
        assertEquals(waist, userData.getWaist(), 0.01);
        assertEquals(hips, userData.getHips(), 0.01);
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UserData userData = new UserData();
        int userID = 3;
        String uname = "Alice";
        String secondName = "Smith";
        String gender = "Female";
        int age = 22;
        int height = 160;
        String username = "alicesmith";
        String email = "alice.smith@example.com";
        String userPass = "mypassword";
        String activityLevel = "Low";
        Boolean premium = true;
        String objective = "Maintain health";
        float neck = 14.0f;
        float waist = 30.0f;
        float hips = 35.0f;

        // Act
        userData.setUserID(userID);
        userData.setUname(uname);
        userData.setSecondName(secondName);
        userData.setGender(gender);
        userData.setAge(age);
        userData.setHeight(height);
        userData.setUsername(username);
        userData.setEmail(email);
        userData.setUserPass(userPass);
        userData.setActivityLevel(activityLevel);
        userData.setPremium(premium);
        userData.setObjective(objective);
        userData.setNeck(neck);
        userData.setWaist(waist);
        userData.setHips(hips);

        // Assert
        assertEquals(userID, userData.getUserID());
        assertEquals(uname, userData.getUname());
        assertEquals(secondName, userData.getSecondName());
        assertEquals(gender, userData.getGender());
        assertEquals(age, userData.getAge());
        assertEquals(height, userData.getHeight());
        assertEquals(username, userData.getUsername());
        assertEquals(email, userData.getEmail());
        assertEquals(userPass, userData.getUserPass());
        assertEquals(activityLevel, userData.getActivityLevel());
        assertEquals(premium, userData.getPremium());
        assertEquals(objective, userData.getObjective());
        assertEquals(neck, userData.getNeck(), 0.01);
        assertEquals(waist, userData.getWaist(), 0.01);
        assertEquals(hips, userData.getHips(), 0.01);
    }

    @Test
    void testSetAndGetRestrictions() {
        // Arrange
        UserData userData = new UserData();
        List<Restrictions> restrictions = new ArrayList<>();
        Restrictions restriction = new Restrictions();
        restrictions.add(restriction);

        // Act
        userData.setRestrictions(restrictions);

        // Assert
        assertNotNull(userData.getRestrictions());
        assertEquals(1, userData.getRestrictions().size());
        assertEquals(restriction, userData.getRestrictions().get(0));
    }

    @Test
    void testSetAndGetWeightGoals() {
        // Arrange
        UserData userData = new UserData();
        List<WeightGoals> weightGoals = new ArrayList<>();
        WeightGoals goal = new WeightGoals();
        weightGoals.add(goal);

        // Act
        userData.setWeightGoals(weightGoals);

        // Assert
        assertNotNull(userData.getWeightGoals());
        assertEquals(1, userData.getWeightGoals().size());
        assertEquals(goal, userData.getWeightGoals().get(0));
    }

    @Test
    void testAddWeightGoal() {
        // Arrange
        UserData userData = new UserData();
        WeightGoals goal = new WeightGoals();

        // Act
        userData.addWeightGoal(goal);

        // Assert
        assertNotNull(userData.getWeightGoals());
        assertEquals(1, userData.getWeightGoals().size());
        assertEquals(goal, userData.getWeightGoals().get(0));
    }
}
