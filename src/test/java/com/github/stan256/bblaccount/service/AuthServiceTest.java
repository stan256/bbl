package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.BblApplication;
import com.github.stan256.bblaccount.helpers.AuthHelper;
import com.github.stan256.bblaccount.helpers.UserHelper;
import com.github.stan256.bblaccount.model.CustomUserDetails;
import com.github.stan256.bblaccount.model.TokenStatus;
import com.github.stan256.bblaccount.model.entity.EmailVerificationToken;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.payload.RegistrationRequest;
import com.github.stan256.bblaccount.model.payload.UpdatePasswordRequest;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
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
        testUserWithId.setId(1L);

        when(userService.existsByEmail(rr.getEmail())).thenReturn(false);
        when(userService.createUser(rr)).thenReturn(testUser);
        when(userService.save(testUser)).thenReturn(testUserWithId);
        assertEquals(testUserWithId, authService.registerUser(rr).get());
    }

    @Test
    public void shouldConfirmEmailRegistrationWhenTokenIsValid(){
        String token = "testtoken";
        User user = UserHelper.buildTestUser();

        EmailVerificationToken evf = new EmailVerificationToken();
        evf.setTokenStatus(TokenStatus.PENDING);
        evf.setExpirationDate(Instant.now().plusSeconds(1));
        evf.setUser(user);
        evf.setToken(token);
        when(emailVerificationTokenService.findByToken(token)).thenReturn(Optional.of(evf));

        assertFalse(user.getEmailVerified());
        Optional<User> confirmedEmailUser = authService.confirmEmailRegistration(token);
        verify(emailVerificationTokenService, times(1)).verifyExpiration(evf);
        assertEquals(TokenStatus.CONFIRMED, evf.getTokenStatus());
        verify(emailVerificationTokenService, times(1)).save(evf);
        assertTrue(user.getEmailVerified());
        verify(userService, times(1)).save(user);
        assertTrue(confirmedEmailUser.isPresent());
    }

    @Test
    public void shouldNotConfirmEmailRegistrationWhenTokenDoesNotExist(){
        String token = "wrongtoken";


        when(emailVerificationTokenService.findByToken(token)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> authService.confirmEmailRegistration(token));
        verify(userService, times(0)).save(any());
        verify(emailVerificationTokenService, times(0)).verifyExpiration(any());
        verify(emailVerificationTokenService, times(0)).save(any());
    }

    @Test
    public void shouldNotConfirmEmailRegistrationWhenUserIsAlreadyRegistered(){
        String token = "token";
        User user = UserHelper.buildTestUser();
        user.setEmailVerified(true);

        EmailVerificationToken evf = new EmailVerificationToken();
        evf.setTokenStatus(TokenStatus.PENDING);
        evf.setExpirationDate(Instant.now().plusSeconds(1));
        evf.setUser(user);
        evf.setToken(token);
        when(emailVerificationTokenService.findByToken(token)).thenReturn(Optional.of(evf));
        Optional<User> confirmedEmailUser = authService.confirmEmailRegistration(token);
        assertTrue(confirmedEmailUser.get().getEmailVerified());
        verify(userService, times(0)).save(any());
        verify(emailVerificationTokenService, times(0)).verifyExpiration(any());
        verify(emailVerificationTokenService, times(0)).save(any());
    }

    @Test
    @Ignore
    public void shouldUpdatePassword(){
        String newPassword = "newPassword";
        String newEncodedPassword = "newEncodedPassword123";

        User user = UserHelper.buildTestUser();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UpdatePasswordRequest upr = new UpdatePasswordRequest();
        upr.setOldPassword(userDetails.getPassword());
        upr.setNewPassword(newPassword);

        when(userService.findByEmail(userDetails.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(upr.getNewPassword())).thenReturn(newEncodedPassword);

        Optional<User> updatedPwdUser = authService.updatePassword(userDetails, upr);
        verify(userService, times(1)).save(user);
        assertEquals(newEncodedPassword, updatedPwdUser.get().getPassword());
    }
}
