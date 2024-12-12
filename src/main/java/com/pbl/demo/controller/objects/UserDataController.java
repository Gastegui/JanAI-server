package com.pbl.demo.controller.objects;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserDataController {
    @Autowired
    private UserDataRepository userRepo;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getUserDataById(@PathVariable int id){
        Optional<UserData> user = userRepo.findById(id);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }

    //If we save the user in the http session, we can get it from here instead of communicating with the database
    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getUserDataBySession(HttpSession session){
        Object userObj = session.getAttribute("user");
        if(userObj instanceof UserData && userObj != null){
            UserData user = (UserData) userObj;
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //This is the same but if we decide to use the security context from spring security
    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getUserBySecurityContext(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserData> user = userRepo.findByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }else return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }


}
