package com.pbl.demo.controller.objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

@RestController
@RequestMapping("/user")
public class UserDataController {
    
    private UserDataRepository userRepo;
    private AdministratorRepository adminRepo;

    @Autowired
    public UserDataController(UserDataRepository userRepo, AdministratorRepository adminRepo){
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
    }
    @GetMapping(value = "/adminByName", produces = { "application/json", "application/xml" })
    public ResponseEntity<Administrator> getAdminByName(@RequestParam String username) {

        Optional<Administrator> admin = adminRepo.findByUsername(username);

        if (admin.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/show", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<UserData>> getUsers() {

        List<UserData> userList = userRepo.findAll();

        if (userList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getUserDataById(@PathVariable int id){
        Optional<UserData> user = userRepo.findById(id);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }


    @GetMapping(value = "/usersByUsername", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserData> getUsersByName(@RequestParam String username) {

        Optional<UserData> users = userRepo.findByUsername(username);

        if (!users.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(users.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/finalDailyCalorieIntakeByUsername", produces = { "application/json", "application/xml" })
    public ResponseEntity<Float> getFinalDailyCalorieIntakeByUsername(@RequestParam String username) {

        Optional<Float> finalDailyCalorieIntake = userRepo.findFinalDailyCalorieIntakeByUsername(username);

        if (finalDailyCalorieIntake.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(finalDailyCalorieIntake.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/waterCounterByUsername", produces = { "application/json", "application/xml" })
    public ResponseEntity<Integer> getWaterCounterByUsername(@RequestParam String username) {

        Optional<Integer> waterCounter = userRepo.findWaterCounterByUsername(username);

        if (waterCounter.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(waterCounter.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/waterIntakeByUserID", produces = { "application/json", "application/xml" })
    public ResponseEntity<Float> getWaterIntakeByUserID(@RequestParam int userID) {

        Optional<Float> waterIntake = userRepo.findWaterIntakeByUserID(userID);

        if (waterIntake.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(waterIntake.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<UserData> addUser(@RequestBody UserData user) {
        
        Optional<UserData> foundUsers = userRepo.findByUsername(user.getUsername());
        if (foundUsers.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
    @PutMapping(value = "/modify/{id}", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<UserData> putUsers(@PathVariable int id, @RequestBody UserData user) {

        Optional<UserData> foundUser = userRepo.findById(id);

        if (foundUser.isPresent()) {

            foundUser.get().setUname(user.getUname());
            foundUser.get().setSecondName(user.getSecondName());
            foundUser.get().setUsername(user.getUsername());
            foundUser.get().setEmail(user.getEmail());
            foundUser.get().setActivityLevel(user.getActivityLevel());
            foundUser.get().setGender(user.getGender());
            foundUser.get().setNeck(user.getNeck());
            foundUser.get().setWaist(user.getWaist());
            userRepo.save(foundUser.get());
            return new ResponseEntity<>(user, HttpStatus.OK);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/modifyCalorieIntake", produces = { "application/json", "application/xml" })
    public ResponseEntity<Float> putFinalDailyCalorieIntake(@RequestParam int userID , @RequestParam float finalDailyCalorieIntake) {

        Optional<UserData> foundUser = userRepo.findById(userID);

        if (foundUser.isPresent()) {
            foundUser.get().setFinalDailyCalorieIntake(finalDailyCalorieIntake);
            userRepo.save(foundUser.get());
            return new ResponseEntity<>(finalDailyCalorieIntake, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/modifyWaterCounter", produces = { "application/json", "application/xml" })
    public ResponseEntity<Integer> putWaterCounter(@RequestParam int userID , @RequestParam int waterCounter) {

        Optional<UserData> foundUser = userRepo.findById(userID);

        if (foundUser.isPresent()) {
            foundUser.get().setWaterCounter(waterCounter+1);
            userRepo.save(foundUser.get());
            return new ResponseEntity<>(waterCounter+1, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UserData> deleteUser(@PathVariable int id) {

        Optional<UserData> foundUsers = userRepo.findById(id);

        if (foundUsers.isPresent()) {

            userRepo.delete(foundUsers.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
