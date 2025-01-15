package com.pbl.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.campaign.Campaign;

class CampaignTest {
    @Test
    void testDefaultConstructor(){
        Campaign campaign = new Campaign();

        //Assert
        assertNotNull(campaign);
        assertEquals(0, campaign.getCampaignID());
        assertNull(campaign.getAdministrator());
        assertNull(campaign.getCampName());
        assertNull(campaign.getCompany());
        assertNull(campaign.getTown());
    }

    @Test
    void testConstructorWithAllParameters(){
        int id = 5;
        String name = "name";
        String town = "twn";
        String company = "conp";
        Administrator admin = new Administrator();

        Campaign camp = new Campaign(id, town, name, company, admin);

        assertEquals(id, camp.getCampaignID());
        assertEquals(name, camp.getCampName());
        assertEquals(town, camp.getTown());
        assertEquals(company, camp.getCompany());
        assertEquals(admin, camp.getAdministrator());
    }

    @Test
    void testConstructorWithoutId(){
        String name = "name";
        String town = "twn";
        String company = "conp";
        Administrator admin = new Administrator();

        Campaign camp = new Campaign(town, name, company, admin);

        assertEquals(name, camp.getCampName());
        assertEquals(town, camp.getTown());
        assertEquals(company, camp.getCompany());
        assertEquals(admin, camp.getAdministrator());
    }

    @Test
    void testGettersAndSetters(){
        int id = 5;
        String name = "name";
        String town = "twn";
        String company = "conp";
        Administrator admin = new Administrator();
        Campaign camp = new Campaign();

        camp.setCampaignID(id);
        camp.setAdministrator(admin);
        camp.setCampName(name);
        camp.setCompany(company);
        camp.setTown(town);

        assertEquals(id, camp.getCampaignID());
        assertEquals(name, camp.getCampName());
        assertEquals(town, camp.getTown());
        assertEquals(company, camp.getCompany());
        assertEquals(admin, camp.getAdministrator());
    }
}
