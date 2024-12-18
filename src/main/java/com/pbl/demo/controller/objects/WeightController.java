package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;*/
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.model.weightGoals.WeightGoals;
//import com.pbl.demo.model.weightGoals.WeightGoalsRepository;

//import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/weight")
public class WeightController {
    @Autowired
    private UserDataRepository userRepo;

    @PostMapping(value = "/addWeight", consumes = { "application/json", "application/xml" }, produces = {
        "application/json", "application/xml" })
    public ResponseEntity<WeightGoals> addWeightGoal(@RequestParam String username, @RequestBody WeightGoals goal) {
        
        Optional<UserData> found_User = userRepo.findByUsername(username);

        if (found_User.isPresent()) {
            goal.setUserData(found_User.get());
            //goal.setUserData(user);
            //goals_Repository.save(goal);
            found_User.get().addWeightGoal(goal);
            //found_User.get().setWeightGoals(found_User.get().getWeightGoals());
            //found_User.get().getweightGoals().add(goal);
            userRepo.save(found_User.get());
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/weightList", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<WeightGoals>> getWeightList(@RequestParam String username) {

        Optional<UserData> user = userRepo.findByUsername(username);
        //List<WeightGoals> listWeight = user.get().getWeightGoals();
        

        /*for(WeightGoals weightGoals: listWeight)
        {
            System.out.println("------------" + weightGoals.getWeightGoalsID() + "-------" + listWeight.size());
        }*/

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<WeightGoals> listWeight = user.get().getWeightGoals();
            return new ResponseEntity(listWeight, HttpStatus.OK);
        }

    }
}
