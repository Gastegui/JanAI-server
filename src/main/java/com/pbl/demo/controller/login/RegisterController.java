package com.pbl.demo.controller.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pbl.demo.controller.ControllerUtils;
import com.pbl.demo.mail.EmailVerificationService;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;



import jakarta.servlet.http.HttpSession;

@Controller
@PreAuthorize("isAnonymous()")
public class RegisterController {
    @Autowired
    private UserDataRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping("/register")
    public String showRegistrationForm(HttpSession session, Model model){
        ControllerUtils.setSessionMessages(session, model);
        model.addAttribute("user", new UserData());
        return "register";
    }

    @PostMapping(value = "/register", consumes = {"application/json", "application/xml"})
    public String registerUser(Model model, HttpSession session, @RequestBody UserData body){
        
        if(body == null){
            System.out.println("El cuerpo esta vacio o no es valido");
            return "redirect:/register";
        }
        
        UserData user = body;


        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        emailVerificationService.sendVerificationCode(user);
        return "redirect:/email-verify";
    }
}
