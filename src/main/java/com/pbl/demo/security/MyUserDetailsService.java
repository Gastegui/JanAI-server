package com.pbl.demo.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pbl.demo.model.users.UserRepository;

import jakarta.servlet.http.HttpSession;

import com.pbl.demo.model.users.Users;

public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HttpSession session;

    //ToDo
    //Rolak db-an sortzeko, admin eta user
    //dbRole etorkizunean aldatu
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String dbUsername, dbPass, dbRole;

        Optional<Users> optUser = userRepo.findByUsername(username);
        if(!optUser.isPresent()){
            throw new UsernameNotFoundException("User wasn't found or doesn't exist");
        }
        Users user = optUser.get();
        dbUsername = user.getUsername();
        dbPass = user.getUserPass();
        dbRole = "A";
        return new org.springframework.security.core.userdetails.User(dbUsername, dbPass, 
            Collections.singleton(new SimpleGrantedAuthority(dbRole)));
    }
    
}
