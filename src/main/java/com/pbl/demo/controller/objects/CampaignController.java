package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.campaign.CampaignRepository;


@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    CampaignRepository cmpRepo;
    @Autowired
    private AdministratorRepository adminRepo;

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns(){
        List<Campaign> campaigns = cmpRepo.findAll();
        if(campaigns.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(campaigns, HttpStatus.OK);
        }
    }
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Campaign> getCampaignById(@PathVariable int id){
        Optional<Campaign> campaign = cmpRepo.findById(id);
        if(!campaign.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(campaign.get(), HttpStatus.OK);
        }
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<Campaign> getCampaignByName(@RequestParam String campName){
        Optional<Campaign> campaign = cmpRepo.findByCampName(campName);
        if(!campaign.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(campaign.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Campaign> addCampaign(@RequestParam int adminID, @RequestBody Campaign campaign) {
        
        Optional<Campaign> found_Campaign = cmpRepo.findByCampName(campaign.getCampName());
        if (found_Campaign.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            
            Optional<Administrator> admin = adminRepo.findById(adminID); 
            if(admin.isPresent())
            {
                campaign.setAdministrator(admin.get());
                cmpRepo.save(campaign);
                return new ResponseEntity<>(campaign, HttpStatus.CREATED);
            }else{
                return ResponseEntity.badRequest().build();
            }
            
        }
    }
}
