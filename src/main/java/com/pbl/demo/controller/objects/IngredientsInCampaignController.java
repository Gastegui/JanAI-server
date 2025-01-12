package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.campaign.Campaign;
import com.pbl.demo.model.campaign.CampaignRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.ingredientsInCampaign.IngredientsInCampaign;
import com.pbl.demo.model.ingredientsInCampaign.IngredientsInCampaignRepository;


@RestController
@RequestMapping("/ingredientCampaign")
public class IngredientsInCampaignController {
    
    CampaignRepository cmpRepo;
    IngredientsInCampaignRepository ingCmpRepo;
    IngredientsRepository ingRepo;

    @Autowired
    public IngredientsInCampaignController(CampaignRepository cmpRepo, IngredientsInCampaignRepository ingCmpRepo, IngredientsRepository ingRepo){
        this.cmpRepo = cmpRepo;
        this.ingCmpRepo = ingCmpRepo;
        this.ingRepo = ingRepo;
    }

    @GetMapping
    public ResponseEntity<List<IngredientsInCampaign>> getAllIngredientsInCampaign(){
        List<IngredientsInCampaign> ingredients = ingCmpRepo.findAll();
        if(ingredients.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
    }
    /*@GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
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
    }*/

    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<IngredientsInCampaign> addIngredientInCampaign(@RequestParam int campaignID, @RequestParam int ingredientID, @RequestBody IngredientsInCampaign ingredientCampaign) {
        
        Optional<Campaign> found_Campaign = cmpRepo.findById(campaignID);
        Optional<Ingredients> ingredient = ingRepo.findById(ingredientID);
        if (!found_Campaign.isPresent() && !ingredient.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            boolean exist = ingCmpRepo.findIngredientsInCampaignByCampaignIDAndIngredientID(found_Campaign.get().getCampaignID(), ingredient.get().getIngredientID());
            if(!exist)
            {
                ingredientCampaign.setCampaign(found_Campaign.get());
                ingredientCampaign.setIngredients(ingredient.get());
                ingCmpRepo.save(ingredientCampaign);
                return new ResponseEntity<>(ingredientCampaign, HttpStatus.CREATED);
            }else{
                return ResponseEntity.badRequest().build();
            }
            
        }
    }
}
