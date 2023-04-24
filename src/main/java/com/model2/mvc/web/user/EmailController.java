package com.model2.mvc.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("text") String text) {
        emailService.sendEmail(to, subject, text);
        return "Email sent successfully!";
    }
}
