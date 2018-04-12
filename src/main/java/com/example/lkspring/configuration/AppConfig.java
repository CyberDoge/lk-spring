package com.example.lkspring.configuration;

import com.example.lkspring.sevice.UserService;
import com.example.lkspring.sevice.UserServiceImpl;
import com.example.lkspring.validator.UserValidator;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;


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


   /* @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}
