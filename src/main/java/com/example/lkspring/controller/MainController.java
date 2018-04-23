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
    private UserService userService;

    @GetMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("home", "message", "text");
    }

    @RequestMapping(value = {"/user/", "/user/home", "/user/profile"}, method = RequestMethod.GET)
    public ModelAndView userPage() {
        ModelAndView model = new ModelAndView();
        var userDet = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.findByUsername(userDet.getUsername());
        model.addObject("user", user);
        model.setViewName("/user/profile");
        return model;
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView editPage(){
        var user = userService.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        var mov =  new ModelAndView("/user/edit", "name", user.getUsername());
        mov.addObject("email", user.getEmail());
        return mov;
    }
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public ModelAndView endEditing(){
        return null;
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
