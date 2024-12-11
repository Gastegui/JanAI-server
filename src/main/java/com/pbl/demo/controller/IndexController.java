package com.pbl.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pbl.demo.model.userData.UserDataRepository;


import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    UserDataRepository userRepo;

    @GetMapping("/")
    public String home(Model model, HttpSession session){
        //This will check if there are any users logged in, if there isn't any, it will redirect
        //to the login menu. Maybe create a home page for unlogged users?
        return "register";
        
        //ToDo, set the users home page
        //Proposal: User account top left, menu top right, daily taken calories in the top center
        //Macros in the middle, register foods down center
        
    }
}
