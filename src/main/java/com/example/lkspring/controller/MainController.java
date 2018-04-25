package com.example.lkspring.controller;

import com.example.lkspring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("home", "message", "text");
    }

    @RequestMapping(value = {"/user/", "/user/home", "/user/profile"}, method = RequestMethod.GET)
    public ModelAndView userPage() {
        ModelAndView model = new ModelAndView();
        var user = userService.findByUsername(getUser().getUsername());
        model.addObject("user", user);
        model.setViewName("/user/profile");
        return model;
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView editPage(ModelAndView mov, BindingResult errors) {
        mov.addObject("name", getUser().getUsername());
        mov.setViewName("/user/edit");
        mov.addObject("errors", errors);
        return mov;
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String endEditing(@RequestBody Model model, HttpServletRequest request, RedirectAttributes redir, BindingResult errors) {
        var user = userService.findByUsernameAndPassword(getUser().getUsername(), bCryptPasswordEncoder.encode(request.getParameter("old_password")));
        if (user == null) errors.rejectValue("badpassword", "wrong password");
        if (userService.findByUsername(request.getParameter("name")) != null)
            errors.rejectValue("badusername", "this username is already taken");
        if (request.getParameter("new password").length() != 0 && request.getParameter("new_password").length() > 6 && request.getParameter("new_password").length() < 32)
            errors.rejectValue("password", "password have to be longer then 6 and shorter then 32");
        if (request.getParameter("new password").length() != 0 && !request.getParameter("confirm_password").equals(request.getParameter("new_password")))
            errors.rejectValue("confirm", "the password are not confirm");
        if (errors.hasErrors()) {
            redir.addFlashAttribute("errors", errors);
            return "redirect:/user/edit";
        }
        user.setUsername(request.getParameter("name"));
        user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("new_password")));
        userService.update(user);
        return "redirect:/user/home";
    }

    @RequestMapping(value = "/user/game", method = RequestMethod.GET)
    public String playGame(Model user) {
        user.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/user/game";
    }

    @PostMapping(value = "/user/endGame")
    public void endGame(@RequestBody com.example.lkspring.model.User user) {
        var postUser = userService.findByUsername(user.getUsername());
        if (postUser.getMaxScore() < user.getMaxScore()) {
            postUser.setMaxScore(user.getMaxScore());
            userService.update(postUser);
        }
        return;
    }

    @RequestMapping(value = "top", method = RequestMethod.GET)
    public ModelAndView printTop() {
        var list = userService.top10MaxScore();
        return new ModelAndView("top", "list", list);
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
