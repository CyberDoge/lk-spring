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
public class RegisterController {
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

    @RequestMapping(value = "/confirmEmail", method = RequestMethod.POST)
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute("userForm") User userForm, BindingResult errors, HttpServletRequest request) {
        userValidator.validate(userForm, errors);
        if (errors.hasErrors()) {
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        userForm.setConfirmationToken(UUID.randomUUID().toString());

        userService.save(userForm);

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();


        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(userForm.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + userForm.getConfirmationToken());

        emailService.sendEmail(registrationEmail);
        modelAndView.setViewName("confirmEmail");
        modelAndView.addObject("user", userForm);
        return modelAndView;
    }


    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
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


}

