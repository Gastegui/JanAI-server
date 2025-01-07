package com.pbl.demo.controller.objects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

//import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserDataController {
    @Autowired
    private UserDataRepository userRepo;
    @Autowired
    private AdministratorRepository adminRepo;

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
    @ResponseBody
    public ResponseEntity<List<UserData>> getUsers() {

        List<UserData> Users_list = userRepo.findAll();

        if (Users_list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(Users_list, HttpStatus.OK);
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

    //If we save the user in the http session, we can get it from here instead of communicating with the database
    /*@GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getUserDataBySession(HttpSession session){
        Object userObj = session.getAttribute("user");
        if(userObj instanceof UserData && userObj != null){
            UserData user = (UserData) userObj;
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }*/

    //This is the same but if we decide to use the security context from spring security
    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<UserData> getUserBySecurityContext(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserData> user = userRepo.findByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }else return new ResponseEntity<>(user.get(), HttpStatus.OK);
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

    @PostMapping(value = "/add", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<UserData> addUser(@RequestBody UserData user) {
        
        Optional<UserData> found_Users = userRepo.findByUsername(user.getUsername());
        if (found_Users.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
    @PutMapping(value = "/modify/{id}", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<UserData> putUsers(@PathVariable int id, @RequestBody UserData user) {

        Optional<UserData> found_User = userRepo.findById(id);

        if (found_User.isPresent()) {

            found_User.get().setUname(user.getUname());
            found_User.get().setSecondName(user.getSecondName());
            found_User.get().setUsername(user.getUsername());
            found_User.get().setEmail(user.getEmail());
            found_User.get().setActivityLevel(user.getActivityLevel());
            found_User.get().setGender(user.getGender());
            found_User.get().setNeck(user.getNeck());
            found_User.get().setWaist(user.getWaist());
            userRepo.save(found_User.get());
            return new ResponseEntity<>(user, HttpStatus.OK);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UserData> deleteUser(@PathVariable int id) {

        Optional<UserData> found_Users = userRepo.findById(id);

        if (found_Users.isPresent()) {

            userRepo.delete(found_Users.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
