package com.example.lkspring.controller;

import com.example.lkspring.model.User;
import com.example.lkspring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("welcome", "message", "text");
    }


    @RequestMapping(value="/user/profile", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
