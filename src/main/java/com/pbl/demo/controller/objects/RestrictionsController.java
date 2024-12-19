package com.pbl.demo.controller.objects;

import java.util.ArrayList;
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

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodClass.FoodClassRepository;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.foodType.FoodTypeRepository;
import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.restrictions.RestrictionsRepository;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

@RestController
@RequestMapping(value = "/restrictions")
public class RestrictionsController {

    @Autowired
    private RestrictionsRepository restrictRepo;
    @Autowired
    private UserDataRepository userRepo;
    @Autowired
    private FoodClassRepository foodClassRepo;
    @Autowired
    private FoodTypeRepository foodTypeRepo;

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Restrictions>> getAllRestrictions(){
        List<Restrictions> restrictions = restrictRepo.findAll();
        if(restrictions.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(restrictions, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/restrictionsByClass", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodClass>> getUsersByName(@RequestParam int userID) {

        Optional<UserData> users = userRepo.findById(userID);
        
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<FoodClass> foodClasses = restrictRepo.findDistinctClasses();
            List<FoodClass> foodClassList = foodClassRepo.findAll();

            for(FoodClass foodClass: foodClasses)
            {
                if(foodClassList.contains(foodClass))
                {
                    foodClassList.remove(foodClass);
                }
            }

            return new ResponseEntity<>(foodClassList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/restrictionsByType", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodType>> getUsersByType(@RequestParam int userID, @RequestParam List<Integer> classIDs) {

        Optional<UserData> users = userRepo.findById(userID);
        
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<FoodType> foodTypeList = foodTypeRepo.findAll();
            for(int classID: classIDs)
            {
                List<FoodType> foodTypes = restrictRepo.findDistinctTypeIDsByClassID(classID);
                
    
                for(FoodType foodType: foodTypes)
                {
                    System.out.println("food type "+ foodType );
                    if(foodTypeList.contains(foodType))
                    {
                        System.out.println("Kendu "+ foodType );
                        foodTypeList.remove(foodType);
                    }
                }
                List<FoodType> foodList = new ArrayList<>(foodTypeList);
                for(FoodType foodType: foodList)
                {
                    if(foodType.getFoodClass().getClassID() != classID)
                    {
                        foodTypeList.remove(foodType);
                    }
                }
    
            }
            
            return new ResponseEntity<>(foodTypeList, HttpStatus.OK);
        }
    }

    /*@GetMapping(value = "/restrictionsByType", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodType>> getUsersByType(@RequestParam int userID, @RequestParam int classID) {

        Optional<UserData> users = userRepo.findById(userID);
        
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<FoodType> foodTypes = restrictRepo.findDistinctTypeIDsByClassID(classID);
            List<FoodType> foodTypeList = foodTypeRepo.findAll();

            for(FoodType foodType: foodTypes)
            {
                if(foodTypeList.contains(foodType))
                {
                    foodTypeList.remove(foodType);
                }
            }

            return new ResponseEntity<>(foodTypeList, HttpStatus.OK);
        }
    }*/

    /*@PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Restrictions> addUser(@RequestBody Restrictions restriction) {
        
        Optional<Restrictions> found_restriction = restrictRepo.findByUsername(restriction.getUsername());
        if (found_restriction.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            restrictRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }*/

}
