package com.pbl.demo.model.food;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;

import com.pbl.demo.model.food_list.FoodList;
import com.pbl.demo.model.has_ingredients.HasIngredients;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodID;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String foodName;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de las caderas debe ser mayor que 0")
    private float proteins;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de las caderas debe ser mayor que 0")
    private float carbs;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de las caderas debe ser mayor que 0")
    private float fats;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de las caderas debe ser mayor que 0")
    private float fiber;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El tamaño de las caderas debe ser mayor que 0")
    private float calories;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HasIngredients> foodTypes;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodList> foodList;

    public Food(){}

    public Food(int foodID, String foodName, float proteins, float carbs, float fats, float fiber, float calories) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
        this.calories = calories;
    }

    public Food(String foodName, float proteins, float carbs, float fats, float fiber, float calories) {
        this.foodName = foodName;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
        this.calories = calories;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }
}