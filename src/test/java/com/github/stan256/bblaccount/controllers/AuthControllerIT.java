package com.github.stan256.bblaccount.controllers;

import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.payload.RegistrationRequest;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import com.github.stan256.bblaccount.service.AuthService;
import com.github.stan256.bblaccount.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ActiveProfiles("integration-test")
class AuthControllerIT {
    @MockBean
    AuthService authService;

    @Mock
    JwtTokenProvider tokenProvider;
    @Mock
    ApplicationEventPublisher publisher;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRegisterUserOnlyWithCorrectData() throws Exception {

        new ArrayList<Integer>();

        RegistrationRequest rr = new RegistrationRequest("test@email.com", "12345password");
        User user = new User();
        user.setEmail(rr.getEmail());
        user.setPassword(rr.getPassword());

        given(authService.registerUser(rr)).willReturn(Optional.of(user));
        mockMvc.perform(post("api/auth/register")
                .param("email", "test@email.com")
                .param("password", "12345password"))
                .andExpect(status().isOk());
        verify(publisher).publishEvent(any());

    }


}
