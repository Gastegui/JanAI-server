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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;
import com.pbl.demo.model.ingredients.IngredientsRepository;

@RestController
@RequestMapping("/food")
public class FoodController {
    
    FoodRepository foodRepo;
    IngredientsRepository ingredientRepo;

    @Autowired
    public FoodController(FoodRepository foodRepo, IngredientsRepository ingredientRepo){
        this.foodRepo = foodRepo;
        this.ingredientRepo = ingredientRepo;
    }
    @GetMapping(value = "/show", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Food>> getFoods() {

        List<Food> food_list = foodRepo.findAll();

        if (food_list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(food_list, HttpStatus.OK);
        }
    }
    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        
        Optional<Food> found_foods = foodRepo.findByFoodName(food.getFoodName());
        if (found_foods.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            foodRepo.save(food);
            return new ResponseEntity<>(food, HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Food> findById(@PathVariable int id){
        Optional<Food> optFood = foodRepo.findById(id);
        if(!optFood.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(optFood.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/search", produces = {"application/json", "application/xml"})
    public ResponseEntity<Food> findByName(@RequestParam String foodName){
        Optional<Food> optFood = foodRepo.findByFoodName(foodName);
        if(!optFood.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(optFood.get(), HttpStatus.OK);
        }
    }

    /*@GetMapping(value = "/search", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Food>> findByIngredient(@RequestParam List<String> ingredients){
        List<Food> foods = foodRepo.findFoodsByIngredients(ingredients);
        if(foods.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }
    }*/
}
