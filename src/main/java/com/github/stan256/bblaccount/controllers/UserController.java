package com.github.stan256.bblaccount.controllers;

import com.github.stan256.bblaccount.model.User;
import com.github.stan256.bblaccount.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(name = "/users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }
}
