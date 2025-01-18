package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;
import com.pbl.demo.model.ingredients.IngredientsRepository;

import jakarta.validation.Valid;

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
    public ResponseEntity<List<Food>> getFoods() {

        List<Food> foodList = foodRepo.findAll();

        if (foodList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(foodList, HttpStatus.OK);
        }
    }
    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Food> addFood(@Valid @RequestBody Food food, BindingResult result) {
        
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("Error: ");
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.badRequest().header("Validation-Error", errorMessages.toString()).build();
        }

        Optional<Food> foundFoods = foodRepo.findByFoodName(food.getFoodName());
        if (foundFoods.isPresent()) {
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
}
