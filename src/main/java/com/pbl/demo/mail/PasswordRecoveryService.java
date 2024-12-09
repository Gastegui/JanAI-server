package com.pbl.demo.mail;

/*import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pbl.demo.model.userData.UserDataRepository;
import com.pbl.demo.model.userData.UserData;

import jakarta.mail.MessagingException;

@Service
public class PasswordRecoveryService {
      @Autowired
    private EmailService emailService;
    @Autowired
    private UserDataRepository userRepository;


    private static HashMap<String, String> verificationTokens = new HashMap<>();


    //Aldatzeko
    public void sendPasswordRecoveryEmail(String email){
        try {
            emailService.sendHtmlMessage(email, "Password recovery",
                getHtmlContent(getResetUrl(generateVerificationToken(email), "/password-recovery/set")));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    //Aldatzeko
    public void sendAdminPasswordSetEmail(String email){
        try {
            emailService.sendHtmlMessage(email, "Set admin password",
                getHtmlContent(getResetUrl(generateVerificationToken(email), "/shelter/set-admin-password")));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getResetUrl(String token, String path){
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(path)
            .queryParam("auth", token)
            .build()
            .toUriString();
        return url;
    }

    private String generateVerificationToken(String email) {
        String token = UUID.randomUUID().toString();
        verificationTokens.put(token, email);
        return token;
    }

    public boolean updatePassword(String token, String newPassword){
        String email = verificationTokens.get(token);
        if(email != null){
            Optional<UserData> userOpt = userRepository.findByEmail(email);
            UserData user = userOpt.get();
            user.setUserPass(newPassword);
            userRepository.save(user);
            verificationTokens.remove(token);
            return true;
        }
        return false;
    }


    public boolean tokenExists(String token){
        return verificationTokens.containsKey(token);
    }

    private String getHtmlContent(String resetUrl) {
        return "<!DOCTYPE html>"
            + "<html lang=\"en\">"
            + "<head>"
            + "    <meta charset=\"UTF-8\">"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
            + "    <style>"
            + "        .email-container { font-family: Arial, sans-serif; color: #333; padding: 20px; max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 10px; background-color: #f8f9fa; }"
            + "        .email-header { background-color: rgb(125, 200, 88); padding: 20px; text-align: center; border-bottom: 1px solid #ddd; border-radius: 10px 10px 0 0; }"
            + "        .email-header img { max-width: 150px; }"
            + "        .email-body { margin: 20px 0; text-align: center; }"
            + "        .reset-button { font-size: 16px; font-weight: bold; background-color: #198754; color: #FFFFFF; padding: 10px 20px; border-radius: 5px; text-decoration: none; display: inline-block; margin: 20px 0; }"
            + "        .email-footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }"
            + "    </style>"
            + "</head>"
            + "<body>"
            + "    <div class=\"email-container\">"
            + "        <div class=\"email-header\">"
            + "            <img src=\"https://drive.google.com/uc?export=view&id=1hPTL2c_qSiZW0J4LZaVErBwMl4tSKqWE\" alt=\"JanAI logo\">"
            + "        </div>"
            + "        <div class=\"email-body\">"
            + "            <h1>Password recovery</h1>"
            + "            <p>We received a password recovery request. If you want to reset your password, click the link below:</p>"
            + "            <a href=\"" + resetUrl + "\" class=\"reset-button\">Reset password</a>"
            + "            <p>If you didn't request for a password recovery, ignore this email.</p>"
            + "        </div>"
            + "        <div class=\"email-footer\">"
            + "            <p>&copy; 2024 JanAI. All rights reserved.</p>"
            + "        </div>"
            + "    </div>"
            + "</body>"
            + "</html>";
    }
}
*/