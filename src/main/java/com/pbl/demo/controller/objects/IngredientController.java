package com.pbl.demo.controller.objects;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;

@RestController
@RequestMapping(value = "/ingredient")
public class IngredientController {
    @Autowired
    IngredientsRepository ingredientRepo;

    @GetMapping(value = "", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Ingredients>> getAllIngredients(){
        List<Ingredients> ingredients = ingredientRepo.findAll();
        if(ingredients.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
    }

    /*@GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Ingredients> getIngredientById(@PathVariable int id){
       Optional<Ingredients> optIngredient = ingredientRepo.findById(id);
        if(!optIngredient.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(optIngredient.get(), HttpStatus.OK);
        }
    }*/

    @GetMapping(value = "/search", produces = {"application/json", "application/xml"})
    public ResponseEntity<Ingredients> getIngredientByName(@RequestParam String ingName){
        Optional<Ingredients> optIngredient = ingredientRepo.findByIngName(ingName);
        if(!optIngredient.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(optIngredient.get(), HttpStatus.OK);
        }
    }

}
