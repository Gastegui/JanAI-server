package com.pbl.demo.mail;
/*import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.model.userData.UserData;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Service
public class EmailVerificationService {
    @Autowired
    private UserDataRepository userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpSession session;


    public void sendVerificationCode(UserData user){
        Integer verificationCode = generateVerificationCode();

        try {
            emailService.sendHtmlMessage(user.getEmail(), "Email verification",
                getHtmlBody(verificationCode));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        session.setAttribute("userToRegister", user);
        session.setAttribute("sentCode", verificationCode);
    }

    public boolean verifyEmail(Integer introducedCode){
        Integer sentCode = (Integer) session.getAttribute("sentCode");
        UserData userToRegister = (UserData) session.getAttribute("userToRegister");

        if(sentCode == null || userToRegister == null){
            session.setAttribute("error", "There is no email to verify");
            return false;
        }

        session.removeAttribute("userToRegister");
        session.removeAttribute("sentCode");

        if(sentCode.equals(introducedCode)){
            userRepo.save(userToRegister);
            session.setAttribute("info", "Registered correctly");
            return true;
        }

        session.setAttribute("error", "Incorrect code");
        return false;
    }

    private Integer generateVerificationCode(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private String getHtmlBody(Integer verificationCode) {
        String htmlTemplate = "<!DOCTYPE html>"
            + "<html lang=\"en\">"
            + "<head>"
            + "    <meta charset=\"UTF-8\">"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
            + "    <style>"
            + "        .email-container { font-family: Arial, sans-serif; color: #333; padding: 20px; max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 10px; background-color: #f8f9fa; }"
            + "        .email-header { background-color: rgb(125, 200, 88); padding: 20px; text-align: center; border-bottom: 1px solid #ddd; border-radius: 10px 10px 0 0; }"
            + "        .email-header img { max-width: 150px; }"
            + "        .email-body { margin: 20px 0; text-align: center; }"
            + "        .verification-code { font-size: 24px; font-weight: bold; background-color: #198754; color: #fff; padding: 10px 20px; border-radius: 5px; display: inline-block; margin: 20px 0; }"
            + "        .email-footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }"
            + "    </style>"
            + "</head>"
            + "<body>"
            + "    <div class=\"email-container\">"
            + "        <div class=\"email-header\">"
            + "            <img src=\"https://drive.google.com/uc?export=view&id=1hPTL2c_qSiZW0J4LZaVErBwMl4tSKqWE\" alt=\"JanAI logo\">"
            + "        </div>"
            + "        <div class=\"email-body\">"
            + "            <h1>Email verification</h1>"
            + "            <p>Thanks for registering on JanAI. Use the code below to verify your email:</p>"
            + "            <div class=\"verification-code\">" + verificationCode + "</div>"
            + "            <p>If you aren't trying to verify your email, ignore this email.</p>"
            + "        </div>"
            + "        <div class=\"email-footer\">"
            + "            <p>&copy; JanAI, all rights reserved.</p>"
            + "        </div>"
            + "    </div>"
            + "</body>"
            + "</html>";

        return htmlTemplate;
    }

 
}
*/