package com.github.stan256.bblaccount.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AuthController.class, includeFilters = @ComponentScan.Filter(classes = EnableWebSecurity.class) )
class AuthControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRegisterUserOnlyWithCorrectData() {
        
    }


}
