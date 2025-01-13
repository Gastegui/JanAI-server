package com.pbl.demo.model;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.user_data.UserData;

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
    }

    @Test
    void testConstructorWithAllParameters() {
        // Arrange
        int userID = 1;
        String uname = "Eki";
        String secondName = "Zuzaeta";
        String gender = "Male";
        int age = 20;
        int height = 180;
        String username = "ekizuzaeta";
        String email = "ekizuzaetae@gmail.com";
        String userPass = "pasahitza123";
        String activityLevel = "Active";
        Boolean premium = true;
        String objective = "Lose weight";
        float neck = 15.5f;
        float waist = 32.0f;
        float hips = 40.0f;
        float waterIntake = 2.0f;
        int waterCounter = 0;
        float finalDailyCalorieIntake = 2000.0f;

        // Act
        UserData userData = new UserData(userID, uname, secondName, gender, age, height, username, email, userPass,
                activityLevel, premium, objective, neck, waist, hips, waterIntake, waterCounter, finalDailyCalorieIntake);

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
    void testConstructorWithoutId() {
        // Arrange
        String uname = "Eki";
        String secondName = "Zuzaeta";
        String gender = "Male";
        int age = 20;
        int height = 180;
        String username = "ekizuzaeta";
        String email = "ekizuzaetae@gmail.com";
        String userPass = "pasahitza123";
        String activityLevel = "Active";
        Boolean premium = true;
        String objective = "Lose weight";
        float neck = 15.5f;
        float waist = 32.0f;
        float hips = 40.0f;
        float waterIntake = 2.0f;
        int waterCounter = 0;
        float finalDailyCalorieIntake = 2000.0f;

        // Act
        UserData userData = new UserData(uname, secondName, gender, age, height, username, email, userPass,
                activityLevel, premium, objective, neck, waist, hips, waterIntake, waterCounter, finalDailyCalorieIntake);

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
}