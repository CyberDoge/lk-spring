package com.example.lkspring.controller;


import com.example.lkspring.model.User;
import com.example.lkspring.repository.TokenRepository;
import com.example.lkspring.sevice.EmailService;
import com.example.lkspring.sevice.TokenService;
import com.example.lkspring.sevice.UserService;
import com.example.lkspring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;


@Controller
public class RegisterController {
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelAndView model, BindingResult errors) {
        model.addObject("userForm", new User());
        model.addObject("errors", errors);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerProcess(@ModelAttribute("userForm") User userForm, BindingResult errors, RedirectAttributes redir, HttpServletRequest request) {
        userValidator.validate(userForm, errors);
        if (errors.hasErrors()) {
            redir.addFlashAttribute("errors", errors);
            return "redirect:/register";
        }

        String confirmationToken =  UUID.randomUUID().toString();
        userService.save(userForm);

        tokenService.save(userForm.getId(), confirmationToken);
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();


        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(userForm.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + confirmationToken);

        emailService.sendEmail(registrationEmail);
        redir.addFlashAttribute("user", userForm);
        return "redirect:/confirmEmail";
    }

    @RequestMapping(value = "/confirmEmail", method = RequestMethod.GET)
    public ModelAndView redirectToConfirmPage(@Valid User userForm) {
        return new ModelAndView("confirmEmail", "user", userForm);
    }


    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findById(tokenService.findUserIdByConfirmationToken(token));

        if (user == null) {
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
            modelAndView.setViewName("welcome");
            return modelAndView;
        } else {
            user.setEnable(1);
            modelAndView.addObject("user", user);
            tokenService.delete(user.getId());
            userService.update(user);
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }


}

