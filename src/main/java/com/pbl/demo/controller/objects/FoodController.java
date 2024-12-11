package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;
import com.pbl.demo.model.ingredients.IngredientsRepository;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    FoodRepository foodRepo;

    @Autowired
    IngredientsRepository ingredientRepo;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Food> findById(@PathVariable int id){
        Optional<Food> optFood = foodRepo.findById(id);
        if(!optFood.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(optFood.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/", produces = {"application/json", "application/xml"})
    public ResponseEntity<Food> findByName(@RequestParam String foodName){
        Optional<Food> optFood = foodRepo.findByFoodName(foodName);
        if(!optFood.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(optFood.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/ingredients", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Food>> findByIngredient(@PathVariable List<String> ingredients){
        List<Food> foods = foodRepo.findFoodsByIngredients(ingredients);
        if(foods.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }
    }
}
