package com.pbl.demo.controller;

import java.time.LocalDate;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.users.*;
import com.pbl.demo.model.food.*;
import com.pbl.demo.model.foodClass.*;
import com.pbl.demo.model.foodGroup.*;
import com.pbl.demo.model.foodType.*;
import com.pbl.demo.model.hasIngredients.*;
import com.pbl.demo.model.ingredients.*;
import com.pbl.demo.model.restrictions.*;
import com.pbl.demo.model.weightGoals.*;


import jakarta.websocket.server.PathParam;


@RestController
@RequestMapping("/user")
public class Controller {

    @Autowired
    UserRepository user_repository;

    /**
     * @brief This method returns the list of Userss in XML and JSON format.
     * @return an HTTP response (OK if there are Userss in the database, not found
     *         if there are
     *         not Userss)
     */
    @GetMapping(value = "/show", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Users>> getUsers() {

        List<Users> Users_list = user_repository.findAll();

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
    public ResponseEntity<Users> getUsers(@PathVariable int id) {

        Optional<Users> Users = user_repository.findById(id);

        if (Users.isPresent()) {
            return new ResponseEntity<>(Users.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(value = "/usersByUsername", produces = { "application/json", "application/xml" })
    public ResponseEntity<Users> getUsersByName(@RequestParam String username) {

        Optional<Users> users = user_repository.findByUsername(username);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity(users, HttpStatus.OK);
        }

    }

    /**
     * @brief This method adds a new Users to the database.
     * @param Food the Users object in XML or JSON format.
     * @return an HTTP response with an OK HTTP status.
     */
    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        
        Optional<Users> found_Users = user_repository.findByUsername(user.getUsername());
        if (found_Users.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            user_repository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
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
    public ResponseEntity<Users> putUsers(@PathVariable int id, @RequestBody Users user) {

        /*user.setActivity("MALE");
        user.setBirthdate(null);
        user.setEmail("anje@gmail.com");
        user.setGender(null);
        user.setHeight(0);
        user.setNeck(0);
        user.setUserPass(null);
        user.setPremium(null);
        user.setWaist(0);*/

        Optional<Users> found_User = user_repository.findById(id);

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
    public ResponseEntity<Users> deleteUser(@PathVariable int id) {

        Optional<Users> found_Users = user_repository.findById(id);

        if (found_Users.isPresent()) {

            user_repository.delete(found_Users.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}


