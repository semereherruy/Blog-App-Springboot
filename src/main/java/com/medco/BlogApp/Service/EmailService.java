package com.medco.BlogApp.Service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
