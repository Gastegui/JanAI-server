package com.pbl.demo.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pbl.demo.controller.ControllerUtils;
import com.pbl.demo.mail.EmailVerificationService;
import com.pbl.demo.model.users.UserRepository;
import com.pbl.demo.model.users.Users;

import jakarta.servlet.http.HttpSession;

@Controller
@PreAuthorize("isAnonymous()")
public class RegisterController {
      @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping("/register")
    public String showRegistrationForm(HttpSession session, Model model){
        ControllerUtils.setSessionMessages(session, model);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Users user, Model model, HttpSession session){
        if(userRepository.findByUsername(user.getUsername()) != null) {
            session.setAttribute("error", "El nombre de usuario ya existe");
            return "redirect:/register";
        }

        if(userRepository.findByEmail(user.getEmail()) != null) {
            session.setAttribute("error", "El email ya está en uso");
            return "redirect:/register";
        }

        if(!user.getUserPass().equals(user.getConfirmPassword())) {
            session.setAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/register";
        }

        if(!ControllerUtils.isPasswordValid(user.getUserPass(), session)) {
            return "redirect:/register";
        }

        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        emailVerificationService.sendVerificationCode(user);
        return "redirect:/email-verify";
    }
}
