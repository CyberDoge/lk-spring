package com.example.lkspring.configuration;

import com.example.lkspring.sevice.EmailService;
import com.example.lkspring.sevice.UserService;
import com.example.lkspring.sevice.UserServiceImpl;
import com.example.lkspring.validator.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Properties;


@Controller
public class AppConfig {
    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }
    @Bean
    UserValidator userValidator(){
        return new UserValidator();
    }

    @Bean
    EmailService emailService(){
        return new EmailService();
    }
  /*  @Bean
    JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;    }
*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
