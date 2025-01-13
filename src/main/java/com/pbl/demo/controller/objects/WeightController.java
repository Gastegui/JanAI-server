package com.pbl.demo.controller.objects;

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

import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;
import com.pbl.demo.model.weight_goals.WeightGoals;
import com.pbl.demo.model.weight_goals.WeightGoalsRepository;

@RestController
@RequestMapping("/weight")
public class WeightController {
   
    private UserDataRepository userRepo;
    private WeightGoalsRepository weightRepo;

    @Autowired
    public WeightController(UserDataRepository userRepo, WeightGoalsRepository weightRepo){
        this.userRepo = userRepo;
        this.weightRepo = weightRepo;
    }

    @PostMapping(value = "/addWeight", consumes = { "application/json", "application/xml" }, produces = {
        "application/json", "application/xml" })
    public ResponseEntity<WeightGoals> addWeightGoal(@RequestParam String username, @RequestBody WeightGoals goal) {
        
        Optional<UserData> foundUser = userRepo.findByUsername(username);
        if (foundUser.isPresent()) {
            goal.setUserData(foundUser.get());
            weightRepo.save(goal);
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    
    
    @GetMapping(value = "/weightList", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<WeightGoals>> getWeightList(@RequestParam String username) {

        Optional<UserData> user = userRepo.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<WeightGoals> listWeight = weightRepo.findAll();
            return new ResponseEntity<>(listWeight, HttpStatus.OK);
        }

    }
}