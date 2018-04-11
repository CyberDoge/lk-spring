package com.example.lkspring.controller;


import com.example.lkspring.model.User;
import com.example.lkspring.sevice.UserService;
import com.example.lkspring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegisterController {
    @Autowired
    UserValidator userValidator;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("userForm") User userForm, BindingResult errors) {
       userValidator.validate(userForm, errors);
        if (errors.hasErrors()) {
            return new ModelAndView("register");
        }
        userService.save(userForm);
        return new ModelAndView("home", "username", userForm.getUsername());
    }
}

