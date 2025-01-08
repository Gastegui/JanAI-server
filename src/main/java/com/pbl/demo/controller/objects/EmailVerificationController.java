package com.pbl.demo.controller.objects;

/*import java.util.List;
import java.util.Optional;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;*/
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;*/
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;



//import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {
    @Autowired
    private UserDataRepository userRepo;

    /*@Autowired
    private EmailVerificationService verifyMail;*/

    @PostMapping(value = "/typedCode", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserData> getVerificationCode(@RequestParam String writtenCode, @RequestParam String verifyMail, @RequestBody UserData user) {

        //Integer sentCode = verifyMail.getVerificationCode();

        if (verifyMail.equals(writtenCode)){
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
