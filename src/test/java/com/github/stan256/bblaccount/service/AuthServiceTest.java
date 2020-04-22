package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.BblApplication;
import com.github.stan256.bblaccount.helpers.AuthHelper;
import com.github.stan256.bblaccount.helpers.UserHelper;
import com.github.stan256.bblaccount.model.TokenStatus;
import com.github.stan256.bblaccount.model.entity.EmailVerificationToken;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.payload.RegistrationRequest;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BblApplication.class})
public class AuthServiceTest {

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
    public void shouldReactOnIfEmailAlreadyInUse() {
        String email = "test@test.com";

        when(userService.existsByEmail(email)).thenReturn(false);
        assertFalse(authService.emailAlreadyExists(email));

        when(userService.existsByEmail(email)).thenReturn(true);
        assertTrue(authService.emailAlreadyExists(email));
    }

    @Test
    public void shouldRegisterUserWithCorrectData() {
        RegistrationRequest rr = AuthHelper.buildTestRegistrationRequest();
        User testUser = new User();
        testUser.setEmail(rr.getEmail());
        testUser.setPassword(rr.getPassword());

        User testUserWithId = new User();
        testUserWithId.setEmail(rr.getEmail());
        testUserWithId.setPassword(rr.getPassword());
        testUserWithId.setId(1);

        when(userService.existsByEmail(rr.getEmail())).thenReturn(false);
        when(userService.createUser(rr)).thenReturn(testUser);
        when(userService.save(testUser)).thenReturn(testUserWithId);
        assertEquals(testUserWithId, authService.registerUser(rr).get());
    }

    @Test
    public void shouldConfirmEmailRegistration(){
        String token = "testtoken";
        User user = UserHelper.buildTestUser();

        EmailVerificationToken evf = new EmailVerificationToken();
        evf.setTokenStatus(TokenStatus.PENDING);
        evf.setExpirationDate(Instant.now().plusSeconds(1));
        evf.setUser(user);
        evf.setToken(token);
        when(emailVerificationTokenService.findByToken(any())).thenReturn(Optional.of(evf));

        assertFalse(user.getEmailVerified());
        Optional<User> confirmedEmailUser = authService.confirmEmailRegistration(token);
        verify(emailVerificationTokenService, times(1)).verifyExpiration(evf);
        assertEquals(TokenStatus.CONFIRMED, evf.getTokenStatus());
        verify(emailVerificationTokenService, times(1)).save(evf);
        assertTrue(user.getEmailVerified());
        verify(userService, times(1)).save(user);
        assertNotNull(confirmedEmailUser);
    }

}
