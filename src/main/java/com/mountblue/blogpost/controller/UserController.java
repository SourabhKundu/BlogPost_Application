package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.controller.web.UserRegistrationDto;
import com.mountblue.blogpost.model.User;
import com.mountblue.blogpost.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private PostController postController;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String showSignupForm(Model model){
        model.addAttribute("user", new UserRegistrationDto());
        return "signup";
    }

    @GetMapping("/signup")
    public String userSignUp(@ModelAttribute("user")UserRegistrationDto userRegistrationDto){
        userService.save(userRegistrationDto);
//        return "redirect:/signup?success";

        return "user Saved" ;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
