package com.github.stan256.bblaccount.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRegisterUserOnlyWithCorrectData() {
        
    }


}
