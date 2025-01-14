package com.pbl.demo.model.ingredients_in_campaign;

import java.util.Date;

import com.pbl.demo.model.ingredients.Ingredients;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.pbl.demo.model.campaign.Campaign;



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
    
    private Date initDate;
    private Date endDate;

    public IngredientsInCampaign(){
    }

    public IngredientsInCampaign(Campaign campaign, Ingredients ingredients, Date initDate, Date endDate) {
        this.campaign = campaign;
        this.ingredients = ingredients;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public IngredientsInCampaign(Date initDate, Date endDate) {
        this.initDate = initDate;
        this.endDate = endDate;
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

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
