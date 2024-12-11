package com.pbl.demo.controller.objects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodGroup.FoodGroupRepository;

@RestController
@RequestMapping("/foodgroup")
public class FoodGroupController {
    @Autowired
    private FoodGroupRepository fgRepo;

    @GetMapping( produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodGroup>> getAllGroups(){
        List<FoodGroup> groups = fgRepo.findAll();
        if(groups.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
    }
}
