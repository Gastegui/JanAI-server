package com.pbl.demo.controller.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.foodList.FoodList;
import com.pbl.demo.model.foodList.FoodListRepository;

@RestController
@RequestMapping("/foodList")
public class FoodListController {
    @Autowired
    FoodListRepository foodListRepo;    

    @GetMapping(value = "/macrosByUser", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Map<String, Double>> getMacrosByUser(@RequestParam int userID) {

        List<FoodList> foodList = foodListRepo.findByUserId(userID);

        if (foodList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } 

        double totalProteins = 0.0;
        double totalCarbs = 0.0;
        double totalFats = 0.0;
        double totalFiber = 0.0;
        double totalCalories = 0.0;

        for (FoodList foodEntry : foodList) {
            totalProteins += foodEntry.getFood().getProteins();
            totalCarbs += foodEntry.getFood().getCarbs();
            totalFats += foodEntry.getFood().getFats();
            totalFiber += foodEntry.getFood().getFiber();
            totalCalories += foodEntry.getFood().getCalories();
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
}
