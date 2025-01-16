package com.pbl.demo.controller.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {
    
    private UserDataRepository userRepo;

    @Autowired
    public EmailVerificationController(UserDataRepository userRepo){
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/typedCode", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserData> getVerificationCode(@RequestParam String writtenCode, @RequestParam String verifyMail, @Valid @RequestBody UserData user, BindingResult result) {
        
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("Error: ");
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.badRequest().header("Validation-Error", errorMessages.toString()).build();
        }
        

        if (verifyMail.equals(writtenCode)){
            user.setFinalDailyCalorieIntake(0f);
            user.setWaterCounter(0);
            user.setWaterIntake(0f);
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
