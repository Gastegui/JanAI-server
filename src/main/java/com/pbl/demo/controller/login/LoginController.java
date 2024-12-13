package com.pbl.demo.controller.login;


import java.util.List;
import java.util.Optional;

import com.pbl.demo.security.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.security.JwtUtil;
import com.pbl.demo.security.AuthRequest;
import org.springframework.web.servlet.View;

import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDataRepository user_repository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * @brief This method validates whether or not the login credentials provided
     * correspond to a registered user.
     * @return an HTTP response (OK if there is a user registered and if the
     * password provided is correct)
     * with the necessary information (JWT Token and Role)
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(authRequest.getUsername());

                // BELOW THIS SNIPPET IS THE CORRECT FLOW WHEN USERS ARE REGISTERED WITH ENCRYPTED PASSWORDS!
                if (authRequest.getUserpass().equals(user.getPassword())) {
                    // Successful login
                    String token = jwtUtil.generateToken(user);
                    String role = user.getAuthorities().iterator().next().getAuthority();
                    Date timeout = jwtUtil.extractExpiration(token);

                    return ResponseEntity.ok(new AuthResponse(token, role, timeout));
                }

                // At the moment, the SQL script used to generate the DB does not provide a valid
                // BCrypt encoded password, so the flow above is used.

            /*
            if (passwordEncoder.matches(authRequest.getUserpass(), user.getUserPass())) {
                // Successful login
                String token = jwtUtil.generateToken(authRequest.getUsername());
                return ResponseEntity.ok(String.valueOf(new AuthResponse(token)));
            }
            */

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    
    /**
     * @brief This method returns the list of Userss in XML and JSON format.
     * @return an HTTP response (OK if there are Userss in the database, not found
     *         if there are
     *         not Userss)
     */
    @GetMapping(value = "/login", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<UserData>> getUserss() {

        List<UserData> Users_list = user_repository.findAll();

    //     if (userList.isEmpty()) {
    //         return ResponseEntity.notFound().build();
    //     } else {
    //         return new ResponseEntity<>(userList, HttpStatus.OK);
    //     }
    // }
    return new ResponseEntity<List<UserData>>(Users_list, HttpStatus.ACCEPTED);
    }

    /**
     * @brief This method returns the information about an Users in XML and JSON
     *        formats.
     * @param id identifier of the Users as query param.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    @GetMapping(value = "/select/{id}", produces =  { "application/json", "application/xml" })
    public ResponseEntity<UserData> getUsers(@RequestParam UserData loguser) {

        Optional<UserData> user = user_repository.findByUsername(loguser.getUsername());
        String password = loguser.getUserPass();
        
    //     if (user.isPresent() && loguser.getUserPass().equals(user.get().getUserPass())) {
    //         return new ResponseEntity<>(user.get(), HttpStatus.OK);
    //     } else {
    //         //Errore bezala ezarri username or password was incorrect
    //         return ResponseEntity.notFound().build();
    //     }

    // }
        return new ResponseEntity<UserData>(loguser, HttpStatus.ACCEPTED);
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

    /**
     * @brief This method deletes an Users stored in the
     *        database.
     * @param id Users id specified as query param.
     * @return an HTTP response (OK if the Users is found, not found if the
     *         Users does not exist in the database)
     */
    /*@DeleteMapping(value = "/deleteUsersRequestParam")
    public ResponseEntity<User> deleteUsersRequestParam(@RequestParam int id) {

        Optional<User> found_Users = user_repository.findById(id);

        if (found_Users.isPresent()) {

            user_repository.delete(found_Users.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }*/
}


