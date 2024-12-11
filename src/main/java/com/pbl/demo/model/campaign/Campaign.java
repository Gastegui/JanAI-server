package com.pbl.demo.model.campaign;

import java.util.Date;
import java.util.List;

import com.pbl.demo.model.foodType.FoodType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.ingredientsInCampaign.IngredientsInCampaign;


@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignID;
    
    private String town;
    private String campaign;
    private String company;


    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientsInCampaign> campaignFoodList;

    @ManyToOne
    @JoinColumn(name = "adminID")
    private Administrator administrator;

    public Campaign(){
    }

    public Campaign(int campaignID, String town, String campaign, String company,
            List<IngredientsInCampaign> campaignFoodList, Administrator administrator) {
        this.campaignID = campaignID;
        this.town = town;
        this.campaign = campaign;
        this.company = company;
        this.campaignFoodList = campaignFoodList;
        this.administrator = administrator;
    }

    public Campaign(String town, String campaign, String company, List<IngredientsInCampaign> campaignFoodList,
            Administrator administrator) {
        this.town = town;
        this.campaign = campaign;
        this.company = company;
        this.campaignFoodList = campaignFoodList;
        this.administrator = administrator;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<IngredientsInCampaign> getCampaignFoodList() {
        return campaignFoodList;
    }

    public void setCampaignFoodList(List<IngredientsInCampaign> campaignFoodList) {
        this.campaignFoodList = campaignFoodList;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    
}
