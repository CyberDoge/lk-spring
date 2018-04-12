package com.example.lkspring.controller;


import com.example.lkspring.model.User;
import com.example.lkspring.sevice.EmailService;
import com.example.lkspring.sevice.UserService;
import com.example.lkspring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Controller
public class UserController {
    @Autowired
    UserValidator userValidator;
    @Autowired
    UserService userService;
    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("userForm") User user, BindingResult errors, HttpServletRequest request) {
        userValidator.validate(user, errors);
        if (errors.hasErrors()) {
            return new ModelAndView("register", "errors", errors);
        }

        // Generate random 36-character string token for confirmation link
        user.setConfirmationToken(UUID.randomUUID().toString());

        userService.save(user);

        String appUrl = request.getScheme() + "://" + request.getServerName();


        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getConfirmationToken());

        //todo
        registrationEmail.setFrom("apleodelva@gmail.com");

        emailService.sendEmail(registrationEmail);

        return new ModelAndView("confirmEmail", "user", user);
    }


    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findByConfirmationToken(token);

        if (user == null) {
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
            modelAndView.setViewName("welcome");
            return modelAndView;
        } else {
            user.setEnable(true);
            modelAndView.addObject("user", user);
            userService.save(user);
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userFrom", new User());
        return "login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("userForm") User userForm) {
        User checkUser = userService.findByUsernameAndPassword(userForm.getUsername(), userForm.getPassword());
        if (checkUser == null)
            return new ModelAndView("login", "error_message", "login or password are incorrect");
        return new ModelAndView("home", "user", checkUser);
    }
}

