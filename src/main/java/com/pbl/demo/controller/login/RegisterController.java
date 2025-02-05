package com.pbl.demo.controller.login;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pbl.demo.controller.ControllerUtils;
import com.pbl.demo.mail.EmailVerificationService;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@PreAuthorize("isAnonymous()")
public class RegisterController {
    
    private UserDataRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private EmailVerificationService emailVerificationService;

    @Autowired
    public RegisterController(UserDataRepository userRepo, PasswordEncoder passwordEncoder, EmailVerificationService emailVerificationService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(HttpSession session, Model model){
        ControllerUtils.setSessionMessages(session, model);
        model.addAttribute("user", new UserData());
        return "register";
    }

    @PostMapping(value = "/register", consumes = {"application/json", "application/xml"})
    public ResponseEntity<EmailVerificationService> registerUser(@RequestBody UserData body){
        
        if(body == null){
            return ResponseEntity.badRequest().build();
        }
        
        Optional<UserData> foundUsers = userRepo.findByUsername(body.getUsername());
        if (foundUsers.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            body.setUserPass(passwordEncoder.encode(body.getUserPass()));
            emailVerificationService.sendVerificationCode(body);
            return new ResponseEntity<>(emailVerificationService, HttpStatus.OK);
        }
    }
}
