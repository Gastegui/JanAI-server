package com.pbl.demo.controller.objects;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;
import com.pbl.demo.model.food_list.FoodList;
import com.pbl.demo.model.food_list.FoodListRepository;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/foodList")
public class FoodListController {
    
    FoodListRepository foodListRepo;
    FoodRepository foodRepo;
    UserDataRepository userDataRepo;    

    @Autowired
    public FoodListController(FoodListRepository foodListRepo, FoodRepository foodRepo, UserDataRepository userDataRepo){
        this.foodListRepo = foodListRepo;
        this.foodRepo = foodRepo;
        this.userDataRepo = userDataRepo;
    }

    @GetMapping(value = "/macrosByUser", produces = { "application/json", "application/xml" })
    public ResponseEntity<Map<String, Double>> getMacrosByUser(@RequestParam int userID) {

        // Obtener la fecha actual
        LocalDate today = LocalDate.now();

        // Filtrar los alimentos por userID y fecha actual
        List<FoodList> foodList = foodListRepo.findByUserId(userID).stream()
                .filter(foodEntry -> foodEntry.getConsumptionDate().isEqual(today))
                .toList();

        if (foodList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } 

        double totalProteins = 0.0;
        double totalCarbs = 0.0;
        double totalFats = 0.0;
        double totalFiber = 0.0;
        double totalCalories = 0.0;

        for (FoodList foodEntry : foodList) {
            totalProteins += (foodEntry.getFood().getProteins() * foodEntry.getGrams())/100;
            totalCarbs += (foodEntry.getFood().getCarbs() * foodEntry.getGrams())/100;
            totalFats += (foodEntry.getFood().getFats() * foodEntry.getGrams())/100;
            totalFiber += (foodEntry.getFood().getFiber() * foodEntry.getGrams())/100;
            totalCalories += (foodEntry.getFood().getCalories() * foodEntry.getGrams())/100;
        }


        totalProteins = Math.round(totalProteins * 100.0) / 100.0;
        totalCarbs = Math.round(totalCarbs * 100.0) / 100.0;
        totalFats = Math.round(totalFats * 100.0) / 100.0;
        totalFiber = Math.round(totalFiber * 100.0) / 100.0;
        totalCalories = Math.round(totalCalories * 100.0) / 100.0;

        Map<String, Double> macros = new HashMap<>();
        macros.put("totalProteins", totalProteins);
        macros.put("totalCarbs", totalCarbs);
        macros.put("totalFats", totalFats);
        macros.put("totalFiber", totalFiber);
        macros.put("totalCalories", totalCalories);

        return new ResponseEntity<>(macros, HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
        "application/json", "application/xml" })
    public ResponseEntity<FoodList> addFoodList(@RequestParam int foodID, @RequestParam int userID, @Valid @RequestBody FoodList foodList, BindingResult result) {
        
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("Error: ");
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.badRequest().header("Validation-Error", errorMessages.toString()).build();
        }
        
        
        Optional<Food> foundFood = foodRepo.findById(foodID);
        Optional<UserData> foundUserData = userDataRepo.findById(userID);
        if (!foundFood.isPresent() || !foundUserData.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
                foodList.setFood(foundFood.get());
                foodList.setUserData(foundUserData.get());
                foodListRepo.save(foodList);
                return new ResponseEntity<>(foodList, HttpStatus.CREATED);
        }
    }
}
