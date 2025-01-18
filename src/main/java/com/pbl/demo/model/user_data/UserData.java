package com.pbl.demo.model.user_data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.weight_goals.WeightGoals;

@Entity
@Table(name = "userData")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @NotNull(message = "The name can not be null")
    @Size(min = 1, max = 50, message = "The name must be between 1 and 50 characters long")
    private String uname;

    @NotNull(message = "Second name can not be null")
    @Size(min = 1, max = 50, message = "Second name must have between 1 and 50 characters")
    private String secondName;

    @NotNull(message = "The gender can not be null")
    @Pattern(regexp = "M|F", message = "The gender must be 'M' o 'F'")
    private String gender;

    @NotNull(message = "Age can not be zero")
    @Min(value = 0, message = "Age should be a positive value")
    @Max(value = 99, message = "The age cannot be more than 99")
    private int age;

    @NotNull(message = "The height can not be zero")
    @Min(value = 0, message = "The height should be a positive value")
    private int height;

    @NotNull(message = "Username can not be null")
    @Size(min = 1, max = 50, message = "The name must be between 1 and 50 characters long")
    private String username;

    @NotNull(message = "The e-mail can not be null")
    @Email(message = "The e-mail must be in a valid format")
    private String email;

    @NotNull(message = "The password can not be null")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String userPass;

    @NotNull(message = "The activity level can not be null")
    @Pattern(regexp = "Sedentary|Light|Moderate|Active|Very Active", message = "Activity level must be one of the values: 'Sedentary', 'Light', 'Moderate', 'Active', 'Very Active'")
    private String activityLevel;

    @NotNull(message = "Premium status cannot be null. It has to be 'true' or 'false'")
    private Boolean premium;

    @NotNull(message = "The objective can not be null")
    @Pattern(regexp = "Lose weight|Gain weight|Keep fit", message = "Objective level must be one of the values: 'Lose weight', 'Gain weight', 'Keep fit'")
    private String objective;

    @NotNull(message = "The neck can not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "The neck measurement must be greater than 0")
    private float neck;

    @NotNull(message = "The waist can not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "The waist measurement must be greater than 0")
    private float waist;

    @NotNull(message = "The hips can not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "The hips measurement must be greater than 0")
    private float hips;

    
    @DecimalMin(value = "0.0", message = "The waterIntake measurement must be greater than 0")
    private Float waterIntake;

    @DecimalMin(value = "0.0", message = "The waterCounter measurement must be greater than 0")
    private Integer waterCounter;
    
    @DecimalMin(value = "0.0", message = "The finalDailyCalorieIntake measurement must be greater than 0")
    private Float finalDailyCalorieIntake;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightGoals> weightGoals = new ArrayList<>();
    /*
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
    private float waterIntake;
    private int waterCounter;
    private float finalDailyCalorieIntake;


    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightGoals> weightGoals = new ArrayList<>();
 */
    public UserData(){
    }

    public UserData(int userID, String uname, String secondName, String gender, int age, int height,
            String username, String email, String userPass, String activityLevel, Boolean premium, String objective,
            float neck, float waist, float hips, float waterIntake, int waterCounter, float finalDailyCalorieIntake) {
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
        this.waterIntake = waterIntake;
        this.waterCounter = waterCounter;
        this.finalDailyCalorieIntake = finalDailyCalorieIntake;
    }

    public UserData(String uname, String secondName, String gender, int age, int height, String username,
            String email, String userPass, String activityLevel, Boolean premium, String objective, float neck,
            float waist, float hips, float waterIntake, int waterCounter, float finalDailyCalorieIntake) {
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
        this.waterIntake = waterIntake;
        this.waterCounter = waterCounter;
        this.finalDailyCalorieIntake = finalDailyCalorieIntake;
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

    public float getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(float waterIntake) {
        this.waterIntake = waterIntake;
    }

    public int getWaterCounter() {
        return waterCounter;
    }

    public void setWaterCounter(int waterCounter) {
        this.waterCounter = waterCounter;
    }

    public float getFinalDailyCalorieIntake() {
        return finalDailyCalorieIntake;
    }

    public void setFinalDailyCalorieIntake(float finalDailyCalorieIntake) {
        this.finalDailyCalorieIntake = finalDailyCalorieIntake;
    }
}