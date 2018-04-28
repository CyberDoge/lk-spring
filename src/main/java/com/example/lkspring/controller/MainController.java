package com.example.lkspring.controller;

import com.example.lkspring.model.User;
import com.example.lkspring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("home", "message", "text");
    }

    @RequestMapping(value = {"/user/home", "/user/profile"}, method = RequestMethod.GET)
    public ModelAndView userPage() {
        ModelAndView model = new ModelAndView();
        var user = userService.findByUsername(getUser().getUsername());
        model.addObject("user", user);
        model.setViewName("/user/profile");
        return model;
    }

    @RequestMapping(value = "/user/game", method = RequestMethod.GET)
    public String playGame(Model user) {
        user.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/user/game";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView editPage(ModelAndView mov, RedirectAttributes redir) {
        mov.addObject("name", getUser().getUsername());
        mov.setViewName("/user/edit");
        mov.addObject("errors", redir.getFlashAttributes().get("errors"));
        return mov;
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String endEditing(HttpServletRequest request, RedirectAttributes redir) {
        List<String> errors = new ArrayList<>();
        var user = userService.findByUsername(getUser().getUsername());
        if (!bCryptPasswordEncoder.matches(request.getParameter("old_password"), user.getPassword()))
            errors.add("wrong password");
        if (userService.findByUsername(request.getParameter("name")) != null)
            errors.add("this username is already taken");
        if (request.getParameter("new_password").length() != 0 && request.getParameter("new_password").length() > 6 && request.getParameter("new_password").length() < 32)
            errors.add("password have to be longer then 6 and shorter then 32");
        if (request.getParameter("new_password").length() != 0 && !request.getParameter("confirm_password").equals(request.getParameter("new_password")))
            errors.add("the password are not confirm");
        if (!errors.isEmpty()) {
            redir.addFlashAttribute("errors", errors);
            return "redirect:/user/edit";
        }

        user.setUsername(request.getParameter("username"));
        user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("new_password")));
        userService.update(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), request.getParameter("new_password"));
        Authentication result = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(result);
        return "redirect:/user/home";
    }

    @PostMapping(value = "/user/endGame")
    public void endGame(@RequestBody User user) {
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

    private UserDetails getUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
