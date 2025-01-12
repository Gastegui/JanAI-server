package com.pbl.demo.security;

import java.util.Collections;
import java.util.Optional;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

public class MyUserDetailsService implements UserDetailsService{
 
    private UserDataRepository userRepo;
    private AdministratorRepository adminRepo;

    @Autowired
    public MyUserDetailsService(UserDataRepository userRepo, AdministratorRepository adminRepo){
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
    }

    //ToDo
    //Rolak db-an sortzeko, admin eta user
    //dbRole etorkizunean aldatu
    /**
     * @brief Method to query a user or administrator based on username
     * @return A spring security User object with all necessary information
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String dbUsername;
        String dbPass;
        String dbRole;

        Optional<UserData> optUser = userRepo.findByUsername(username);
        Optional<Administrator> optAdmin = adminRepo.findByUsername(username);
        if(optUser.isEmpty() && optAdmin.isEmpty()) {
            throw new UsernameNotFoundException("User wasn't found or doesn't exist");
        }
        if (optUser.isPresent()) {
            UserData userData = optUser.get();
            dbUsername = userData.getUsername();
            dbPass = userData.getUserPass();
            dbRole = "USER";
        } else {
            Administrator admin = optAdmin.get();
            dbUsername = admin.getUsername();
            dbPass = admin.getUserPass();
            dbRole = "ADMIN";
        }
        return new org.springframework.security.core.userdetails.User(dbUsername, dbPass, 
            Collections.singleton(new SimpleGrantedAuthority(dbRole))
        );
    }
    
}
