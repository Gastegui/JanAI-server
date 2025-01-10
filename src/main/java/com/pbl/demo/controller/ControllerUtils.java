package com.pbl.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public final class ControllerUtils {
    static Random random = new Random();
    static final String ERROR = "error";

    public static void setSessionMessages(HttpSession session, Model model){
        String errorMsg = (String) session.getAttribute(ERROR);
        if (errorMsg != null) {
            model.addAttribute("errorMsg", errorMsg);
            session.removeAttribute(ERROR);
        }

        String infoMsg = (String) session.getAttribute("info");
        if(infoMsg != null) {
            model.addAttribute("infoMsg", infoMsg);
            session.removeAttribute("info");
        }
    }

    public static boolean isPasswordValid(String password, HttpSession session) {
        if (password.length() < 8) {
            session.setAttribute(ERROR, 
                "La contraseña debe contener al menos 8 caracteres.");
            return false;
        }

        // Patrón regex para validar la contraseña (que tenga letras y numeros)
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) session.setAttribute(ERROR, 
            "La contraseña debe contener letras y numeros");
        return matcher.matches();
    }

    public static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                userDetails = (UserDetails) authentication.getPrincipal();
                return userDetails.getUsername();
            } else {
                return authentication.getName(); // En caso de autenticaciones simples como OAuth2
            }
        }
        return null;
    }

    public static String getLoggedInRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authString = "";
        if (authentication != null && authentication.isAuthenticated() && (authentication.getPrincipal() instanceof UserDetails userDetails)) {
                userDetails = (UserDetails) authentication.getPrincipal();
                // Suponiendo que solo hay un rol, devolvemos el primero
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority != null){
                        authString = authority.getAuthority();
                    }
                }
                
            
        }
        if(authString != null){
            return authString;
        } else return null;
    }

    public static boolean hasAuthority(String auth){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals(auth));
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public static <T> List<T> iterableToArrayList(Iterable<T> iterable) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (T item : iterable) {
            arrayList.add(item);
        }
        return arrayList;
    }
}
