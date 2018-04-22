package com.example.lkspring.controller;

import com.example.lkspring.model.User;
import com.example.lkspring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("home", "message", "text");
    }

    @RequestMapping(value = {"/user/", "/user/home", "/user/profile"}, method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Remember Me");
        model.addObject("message", "This page is for ROLE_USER only!");
        model.addObject("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.setViewName("/user/profile");
        return model;
    }

    @RequestMapping(value = "/user/game", method = RequestMethod.GET)
    public String playGame(Model user) {
        user.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/user/game";
    }

    @PostMapping(value = "/user/endGame")
    public void endGame(@RequestBody User user) {
        var postUser = userService.findByUsername(user.getUsername());
        if(postUser.getMaxScore() < user.getMaxScore()){
            postUser.setMaxScore(user.getMaxScore());
            userService.update(postUser);
        }
        return;
    }
    @RequestMapping(value = "top", method = RequestMethod.GET)
    public ModelAndView printTop(){
        var list =  userService.top10MaxScore();
        return new ModelAndView("top", "list", list);
    }

}
