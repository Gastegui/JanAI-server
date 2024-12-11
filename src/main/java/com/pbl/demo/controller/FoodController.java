package com.pbl.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.food.Food;
import com.pbl.demo.model.food.FoodRepository;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodRepository food_repository;

    /**
     * @brief This method returns the list of Userss in XML and JSON format.
     * @return an HTTP response (OK if there are Userss in the database, not found
     *         if there are
     *         not Userss)
     */
    @GetMapping(value = "/show", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Food>> getFoods() {

        List<Food> food_list = food_repository.findAll();

        if (food_list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(food_list, HttpStatus.OK);
        }
    }

    /**
     * @brief This method returns the information about an Users in XML and JSON
     *        formats.
     * @param id identifier of the Users as query param.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @GetMapping(value = "/select/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<Food> getFoodByID(@PathVariable int id) {

        Optional<Food> food = food_repository.findById(id);

        if (food.isPresent()) {
            return new ResponseEntity<>(food.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * @brief This method adds a new Users to the database.
     * @param Food the Users object in XML or JSON format.
     * @return an HTTP response with an OK HTTP status.
     */
    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        
        Optional<Food> found_foods = food_repository.findByFoodName(food.getFoodName());
        if (found_foods.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            food_repository.save(food);
            return new ResponseEntity<>(food, HttpStatus.CREATED);
        }
    }

    /**
     * @brief This method modifies the information about an Users stored in the
     *        database.
     * @param Food the Users object in XML or JSON format.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @PutMapping(value = "/modify/{id}", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Food> putFood(@PathVariable int id, @RequestBody Food food) {

        Optional<Food> found_food = food_repository.findById(id);

        if (found_food.isPresent()) {

            found_food.get().setFoodName(food.getFoodName());
            found_food.get().setCarbs(food.getCarbs());
            found_food.get().setFats(food.getFats());
            found_food.get().setFiber(food.getFiber());
            found_food.get().setProteins(food.getProteins());

            food_repository.save(found_food.get());
            return new ResponseEntity<>(food, HttpStatus.OK);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @brief This method deletes an Users stored in the
     *        database.
     * @param id Users id specified in the path.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Food> deleteFood(@PathVariable int id) {

        Optional<Food> found_food = food_repository.findById(id);

        if (found_food.isPresent()) {

            food_repository.delete(found_food.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}


