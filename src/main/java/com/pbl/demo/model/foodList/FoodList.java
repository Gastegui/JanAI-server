package com.pbl.demo.model.foodList;

import java.util.Date;
import java.util.List;

import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.hasIngredients.HasIngredients;
import com.pbl.demo.model.ingredients.Ingredients;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@IdClass(FoodList.class)
@Table(name = "foodList")
public class FoodList {

    @Id
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userData;
    @Id
    @ManyToOne
    @JoinColumn(name = "foodID")
    private Food food;

    private Date consumption_date;
    private String meal;
    
    

    public FoodList(){
    }



    public FoodList(UserData userData, Food food, Date consumption_date, String meal) {
        this.userData = userData;
        this.food = food;
        this.consumption_date = consumption_date;
        this.meal = meal;
    }



    public FoodList(Date consumption_date, String meal) {
        this.consumption_date = consumption_date;
        this.meal = meal;
    }



    public UserData getUserData() {
        return userData;
    }



    public void setUserData(UserData userData) {
        this.userData = userData;
    }



    public Food getFood() {
        return food;
    }



    public void setFood(Food food) {
        this.food = food;
    }



    public Date getConsumption_date() {
        return consumption_date;
    }



    public void setConsumption_date(Date consumption_date) {
        this.consumption_date = consumption_date;
    }



    public String getMeal() {
        return meal;
    }



    public void setMeal(String meal) {
        this.meal = meal;
    }

    


    
}
