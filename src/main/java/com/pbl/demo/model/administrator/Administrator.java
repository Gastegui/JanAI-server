package com.pbl.demo.model.administrator;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.pbl.demo.model.campaign.Campaign;

@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminID;

    private String uname;
    private String surname;
    private String username;
    private String email;
    private String userPass;

    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Campaign> campaignList;

    public Administrator(){
    }

    public Administrator(int adminID, String uname, String surname, String username, String email, String userPass) {
        this.adminID = adminID;
        this.uname = uname;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.userPass = userPass;
    }



    

    public Administrator(String uname, String surname, String username, String email, String userPass) {
        this.uname = uname;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.userPass = userPass;
    }



    public int getAdminID() {
        return adminID;
    }



    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }



    public String getUname() {
        return uname;
    }



    public void setUname(String uname) {
        this.uname = uname;
    }



    public String getSurname() {
        return surname;
    }



    public void setSurname(String surname) {
        this.surname = surname;
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



    /*public List<Campaign> getCampaignList() {
        return campaignList;
    }



    public void setCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }*/



}
