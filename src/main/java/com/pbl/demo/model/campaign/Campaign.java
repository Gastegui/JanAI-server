package com.pbl.demo.model.campaign;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.ingredients_in_campaign.IngredientsInCampaign;


@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignID;
    
    @NotNull(message = "The town can not be null")
    @Size(min = 1, max = 50, message = "The town must be between 1 and 50 characters long")
    private String town;
    
    @NotNull(message = "The campName can not be null")
    @Size(min = 1, max = 50, message = "The campName must be between 1 and 50 characters long")
    private String campName;
    
    @NotNull(message = "The company can not be null")
    @Size(min = 1, max = 50, message = "The company must be between 1 and 50 characters long")
    private String company;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientsInCampaign> campaignFoodList;

    @NotNull(message = "The administrator can not be null")
    @ManyToOne
    @JoinColumn(name = "adminID")
    private Administrator administrator;

    public Campaign(){
    }

    

    public Campaign(int campaignID, String town, String campName, String company,Administrator administrator) {
        this.campaignID = campaignID;
        this.town = town;
        this.campName = campName;
        this.company = company;
        this.administrator = administrator;
    }





    public Campaign(String town, String campName, String company,
            Administrator administrator) {
        this.town = town;
        this.campName = campName;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    
}
