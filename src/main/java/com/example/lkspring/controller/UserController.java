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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute("userForm") User userForm, BindingResult errors, HttpServletRequest request) {
        System.out.println("---------registered-------------");
        userValidator.validate(userForm, errors);
        if (errors.hasErrors()) {
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        // Generate random 36-character string token for confirmation link
        userForm.setConfirmationToken(UUID.randomUUID().toString());

        userService.save(userForm);

        String appUrl = request.getScheme() + "://" + request.getServerName();


        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(userForm.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + userForm.getConfirmationToken());

        //todo
        registrationEmail.setFrom("apleodelva@gmail.com");

        emailService.sendEmail(registrationEmail);
        System.out.println("---------registered-------------");
        System.out.println(registrationEmail.toString());
        modelAndView.setViewName("confirmEmail");
        modelAndView.addObject("user", userForm);
        return modelAndView;
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

