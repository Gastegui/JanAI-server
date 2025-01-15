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
    
    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String town;
    
    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String campName;
    
    @NotNull(message = "El tipo de alimento no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String company;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientsInCampaign> campaignFoodList;

    @NotNull(message = "El tipo de alimento no puede ser nulo")
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
