package com.model2.mvc.common;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
