package com.pbl.demo.model.ingredientsInCampaign;
import java.io.Serializable;

public class IngredientsInCampaignPK implements Serializable {
    private int campaign;
    private int ingredient;

    // Constructor vacío
    public IngredientsInCampaignPK() {}

    // Constructor completo
    public IngredientsInCampaignPK(int campaign, int ingredient) {
        this.campaign = campaign;
        this.ingredient = ingredient;
    }

    
}