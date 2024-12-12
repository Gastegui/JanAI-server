package com.pbl.demo.controller.objects;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

@RestController
@RequestMapping("/user")
public class UserDataController {
    @Autowired
    private UserDataRepository userRepo;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getCampaignById(@PathVariable int id){
        Optional<UserData> user = userRepo.findById(id);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }
}
