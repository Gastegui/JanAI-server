package com.pbl.demo.controller.objects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodClass.FoodClassRepository;

@RestController
@RequestMapping("/foodclass")
public class FoodClassController {
    @Autowired
    private FoodClassRepository fcRepo;

    @GetMapping
    public ResponseEntity<List<FoodClass>> getAllClasses(){
        List<FoodClass> classes = fcRepo.findAll();
        if(classes.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(classes, HttpStatus.OK);
        }
    }
}
