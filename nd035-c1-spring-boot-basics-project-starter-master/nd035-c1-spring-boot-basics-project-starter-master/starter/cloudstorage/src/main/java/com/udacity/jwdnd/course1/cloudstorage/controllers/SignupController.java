package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupPage(@ModelAttribute("createUser") User user){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute("createUser") User user, Model model){
        String signupFail = null;

        if(!userService.isHaveUsername(user.getUsername())){
            signupFail = "Username '" + user.getUsername() + "' already exits.";
        }

        if(signupFail == null){
            int addRow = userService.createUser(user);
            if(addRow <0){
                signupFail = "Sign up fail! Please sign up again...";
            }
        }

        if(signupFail != null){
            model.addAttribute("signupFail", signupFail);
            return "signup";
        }

        model.addAttribute("signupSuccess", true);
        return "login";

    }
}
