package com.pbl.demo.controller.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {
    
    private UserDataRepository userRepo;

    @Autowired
    public EmailVerificationController(UserDataRepository userRepo){
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/typedCode", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserData> getVerificationCode(@RequestParam String writtenCode, @RequestParam String verifyMail, @RequestBody UserData user) {


        if (verifyMail.equals(writtenCode)){
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
