package com.example.lkspring.controller;


import com.example.lkspring.model.User;
import com.example.lkspring.sevice.EmailService;
import com.example.lkspring.sevice.UserService;
import com.example.lkspring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
    public String register(ModelAndView model, HttpServletRequest redirectAttributes) {
        model.addObject("userForm", new User());
        Enumeration<String> reqEnum = redirectAttributes.getParameterNames();
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute("userForm") @Valid User userForm, BindingResult errors, HttpServletRequest request, RedirectAttributes redir) {
        userValidator.validate(userForm, errors);
        if (errors.hasErrors()) {
            List<String> erList = new ArrayList(errors.getErrorCount());
            for (ObjectError e : errors.getAllErrors()) {
                erList.add(e.getCode());
            }
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
        modelAndView.setViewName("redirect:/confirmEmail");
        redir.addFlashAttribute("user", userForm);
        return modelAndView;
    }
    @RequestMapping(value = "/confirmEmail", method = RequestMethod.GET)
    public ModelAndView redirectToConfirmPage(@Valid User userForm){
        return new ModelAndView("confirmEmail", "user", userForm);
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

