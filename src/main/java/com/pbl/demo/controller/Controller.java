package com.pbl.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import com.pbl.demo.model.userData.*;
import com.pbl.demo.model.weightGoals.*;



@RestController
@RequestMapping("/user")
public class Controller {

    @Autowired
    private UserDataRepository user_repository;
    @Autowired
    private WeightGoalsRepository goals_Repository;
    @Autowired
    private AdministratorRepository admin_Repository;
    


    
    /**
     * @brief This method returns the list of Userss in XML and JSON format.
     * @return an HTTP response (OK if there are Userss in the database, not found
     *         if there are
     *         not Userss)
     */
    @GetMapping(value = "/show", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<UserData>> getUsers() {

        List<UserData> Users_list = user_repository.findAll();

        if (Users_list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(Users_list, HttpStatus.OK);
        }
    }

    

    /**
     * @brief This method returns the information about an Users in XML and JSON
     *        formats.
     * @param id identifier of the Users as query param.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @GetMapping(value = "/select/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserData> getUsers(@PathVariable int id) {

        Optional<UserData> Users = user_repository.findById(id);

        if (Users.isPresent()) {
            return new ResponseEntity<>(Users.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(value = "/usersByUsername", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserData> getUsersByName(@RequestParam String username) {

        Optional<UserData> users = user_repository.findByUsername(username);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(users.get(), HttpStatus.OK);
        }

    }

    @GetMapping(value = "/adminByName", produces = { "application/json", "application/xml" })
    public ResponseEntity<Administrator> getAdminByName(@RequestParam String username) {

        Optional<Administrator> admin = admin_Repository.findByUsername(username);

        if (admin.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }

    }

    @GetMapping(value = "/weightList", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<WeightGoals>> getWeightList(@RequestParam String username) {

        Optional<UserData> user = user_repository.findByUsername(username);
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

    /**
     * @brief This method adds a new Users to the database.
     * @param Food the Users object in XML or JSON format.
     * @return an HTTP response with an OK HTTP status.
     */
    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<UserData> addUser(@RequestBody UserData user) {
        
        Optional<UserData> found_Users = user_repository.findByUsername(user.getUsername());
        if (found_Users.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            user_repository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/addWeight", consumes = { "application/json", "application/xml" }, produces = {
        "application/json", "application/xml" })
    public ResponseEntity<WeightGoals> addWeightGoal(@RequestParam String username, @RequestBody WeightGoals goal) {
        
        Optional<UserData> found_User = user_repository.findByUsername(username);

        if (found_User.isPresent()) {
            goal.setUserData(found_User.get());
            //goal.setUserData(user);
            //goals_Repository.save(goal);
            found_User.get().addWeightGoal(goal);
            //found_User.get().setWeightGoals(found_User.get().getWeightGoals());
            //found_User.get().getweightGoals().add(goal);
            user_repository.save(found_User.get());
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @brief This method modifies the information about an Users stored in the
     *        database.
     * @param Food the Users object in XML or JSON format.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @PutMapping(value = "/modify/{id}", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<UserData> putUsers(@PathVariable int id, @RequestBody UserData user) {

        Optional<UserData> found_User = user_repository.findById(id);

        if (found_User.isPresent()) {

            found_User.get().setUname(user.getUname());
            found_User.get().setSecondName(user.getSecondName());
            found_User.get().setUsername(user.getUsername());
            found_User.get().setEmail(user.getEmail());
            found_User.get().setActivity(user.getActivity());
            found_User.get().setGender(user.getGender());
            found_User.get().setNeck(user.getNeck());
            found_User.get().setWaist(user.getWaist());
            user_repository.save(found_User.get());
            return new ResponseEntity<>(user, HttpStatus.OK);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @brief This method deletes an Users stored in the
     *        database.
     * @param id Users id specified in the path.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UserData> deleteUser(@PathVariable int id) {

        Optional<UserData> found_Users = user_repository.findById(id);

        if (found_Users.isPresent()) {

            user_repository.delete(found_Users.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}


