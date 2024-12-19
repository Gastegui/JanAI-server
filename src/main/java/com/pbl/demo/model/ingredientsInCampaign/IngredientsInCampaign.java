package com.pbl.demo.model.ingredientsInCampaign;

import java.util.Date;

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

import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.hasIngredients.HasIngredients;



@Entity
@IdClass(IngredientsInCampaign.class)
@Table(name = "ingredientsInCampaign")
public class IngredientsInCampaign {

    @Id
    @ManyToOne
    @JoinColumn(name = "campaignID")
    private Campaign campaign;
    @Id
    @ManyToOne
    @JoinColumn(name = "ingredientID")
    private Ingredients ingredients;
    
    private Date init_date;
    private Date end_date;

    public IngredientsInCampaign(){
    }

    public IngredientsInCampaign(Campaign campaign, Ingredients ingredients, Date init_date, Date end_date) {
        this.campaign = campaign;
        this.ingredients = ingredients;
        this.init_date = init_date;
        this.end_date = end_date;
    }

    public IngredientsInCampaign(Date init_date, Date end_date) {
        this.init_date = init_date;
        this.end_date = end_date;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public Date getInit_date() {
        return init_date;
    }

    public void setInit_date(Date init_date) {
        this.init_date = init_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

}
