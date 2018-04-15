package com.example.lkspring.controller;

import com.example.lkspring.model.User;
import com.example.lkspring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

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
