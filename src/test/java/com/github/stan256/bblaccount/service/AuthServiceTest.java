package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.helpers.AuthHelper;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.payload.RegistrationRequest;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import com.github.stan256.bblaccount.helpers.UserHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceTest {
    
    @Mock
    private UserService userService;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private EmailVerificationTokenService emailVerificationTokenService;
    @Mock
    private UserDeviceService userDeviceService;
    @Mock
    private PasswordResetTokenService passwordResetTokenService;
    
    @InjectMocks
    private AuthService authService;
    
    
    @Test
    public void shouldReactOnIfEmailAlreadyInUse(){
        String email = "test@test.com";
        
        when(userService.existsByEmail(email)).thenReturn(false);
        assertFalse(authService.emailAlreadyExists(email));
        
        when(userService.existsByEmail(email)).thenReturn(true);
        assertTrue(authService.emailAlreadyExists(email));
    }
    
    @Test
    public void shouldRegisterUserWithCorrectData(){
        RegistrationRequest rr = AuthHelper.buildTestRegistrationRequest();
        User testUser = User.builder()
                .email(rr.getEmail())
                .password(rr.getPassword())
                .build();
        User testUserWithId = User.builder()
                .email(rr.getEmail())
                .password(rr.getPassword())
                .id(1)
                .build();

        when(userService.existsByEmail(rr.getEmail())).thenReturn(false);
        when(userService.createUser(rr)).thenReturn(testUser);
        when(userService.save(testUser)).thenReturn(testUserWithId);
        assertEquals(testUserWithId, authService.registerUser(rr).get());
    }
    
    
    
    

}
