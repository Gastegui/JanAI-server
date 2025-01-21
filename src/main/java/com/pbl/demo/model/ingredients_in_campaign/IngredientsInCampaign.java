package com.pbl.demo.model.ingredients_in_campaign;

import java.time.LocalDate;

import com.pbl.demo.model.ingredients.Ingredients;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    
    @NotNull(message = "The initDate can not be null. It has to be in this format: yyyy-MM-dd")
    @FutureOrPresent(message = "The initDate must be today or a future date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate initDate;

    @NotNull(message = "The endDate can not be null. It has to be in this format: yyyy-MM-dd")
    @FutureOrPresent(message = "The initDate must be today or a future date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public IngredientsInCampaign(){
    }

    public IngredientsInCampaign(Campaign campaign, Ingredients ingredients, LocalDate initDate, LocalDate endDate) {
        this.campaign = campaign;
        this.ingredients = ingredients;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public IngredientsInCampaign(LocalDate initDate, LocalDate endDate) {
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

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
