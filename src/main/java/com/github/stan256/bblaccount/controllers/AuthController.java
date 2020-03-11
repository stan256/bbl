package com.github.stan256.bblaccount.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping(value = "users")
public class AuthController {
    @PostMapping("api/auth/login")
    public void login(){

    }

    @PostMapping("api/auth/registration")
    public void registration(){

    }
}
