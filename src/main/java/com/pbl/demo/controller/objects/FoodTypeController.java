package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.foodType.FoodTypeRepository;

@RestController
@RequestMapping("/foodtype")
public class FoodTypeController {
    @Autowired
    private FoodTypeRepository ftRepo;

    @GetMapping( produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodType>> getAllTypes(){
        List<FoodType> types = ftRepo.findAll();
        if(types.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(types, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/selectByTypeName", produces = { "application/json", "application/xml" })
    public ResponseEntity<FoodType> getTypeByTypeName(@RequestParam String typeName){
        Optional<FoodType> type = ftRepo.findByTypeName(typeName);
        if(type.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(type.get(), HttpStatus.OK);
        }
    }
}
