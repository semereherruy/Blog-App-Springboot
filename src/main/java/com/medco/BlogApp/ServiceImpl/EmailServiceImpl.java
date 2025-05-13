package com.medco.BlogApp.ServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.medco.BlogApp.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
  @Autowired
  private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String htmlBody){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setFrom("semereherruy27@gmail.com");

            mailSender.send(message);
            System.out.println("Email sent!");
        } catch (MessagingException e) {
            System.err.println("Fail to send the email" + e.getMessage());
        }
    }
}
