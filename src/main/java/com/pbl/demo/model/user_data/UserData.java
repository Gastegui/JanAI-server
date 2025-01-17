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

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String uname;

    @NotNull(message = "El segundo nombre no puede ser nulo")
    @Size(min = 1, max = 100, message = "El segundo nombre debe tener entre 1 y 100 caracteres")
    private String secondName;

    @NotNull(message = "El género no puede ser nulo")
    @Pattern(regexp = "^[MF]$", message = "El género debe ser 'M' o 'F'")
    private String gender;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad debe ser un valor positivo")
    @Max(value = 150, message = "La edad no puede ser mayor a 150")
    private int age;

    @NotNull(message = "La altura no puede ser nula")
    @Min(value = 0, message = "La altura debe ser un valor positivo")
    private int height;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre de usuario debe tener entre 5 y 50 caracteres")
    private String username;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 1, message = "La contraseña debe tener al menos 6 caracteres")
    private String userPass;

    @NotNull(message = "El nivel de actividad no puede ser nulo")
    @Pattern(regexp = "Sedentary|Light|Moderate|Active|Very Active", message = "El nivel de actividad debe ser uno de los valores: 'Sedentary', 'Light', 'Moderate', 'Active', 'Very Active'")
    private String activityLevel;

    @NotNull(message = "El estado de premium no puede ser nulo")
    private Boolean premium;

    @NotNull(message = "El objetivo no puede ser nulo")
    @Pattern(regexp = "Lose weight|Gain weight|Keep fit", message = "El objetivo debe ser uno de los valores: 'Lose weight', 'Gain weight', 'Keep fit'")
    private String objective;

    @NotNull(message = "El tamaño del cuello no puede ser nulo NECK")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño del cuello debe ser mayor que 0 NECK")
    private float neck;

    @NotNull(message = "El tamaño de la cintura no puede ser nulo WAIST")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de la cintura debe ser mayor que 0 WAIST")
    private float waist;

    @NotNull(message = "El tamaño de las caderas no puede ser nulo HIPS")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de las caderas debe ser mayor que 0 HIPS")
    private float hips;

    
    @DecimalMin(value = "0.0", message = "El tamaño de las caderas debe ser mayor que 0 HIPS")
    private Float waterIntake;

    @DecimalMin(value = "0.0", message = "El tamaño de las caderas debe ser mayor que 0 HIPS")
    private Integer waterCounter;
    
    @DecimalMin(value = "0.0", message = "El tamaño de las caderas debe ser mayor que 0 HIPS")
    private Float finalDailyCalorieIntake;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restrictions> restrictions;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightGoals> weightGoals = new ArrayList<>();
   
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