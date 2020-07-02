package com.github.stan256.bblaccount.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Transactional
@RequestMapping("/api/private")
public class TestPrivateController {

    @GetMapping("/")
    public int get5() {
        return 5;
    }
}

